package com.ysh.seckill.impl;

import com.ysh.seckill.common.IdWorker;
import com.ysh.seckill.dao.SeckillOrderRepository;
import com.ysh.seckill.entity.SeckillGoods;
import com.ysh.seckill.entity.SeckillOrder;
import com.ysh.seckill.service.SeckillGoodsService;
import com.ysh.seckill.service.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author joey
 * @date 2018/05/30 00:22
 */
@Service
@Slf4j
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private SeckillOrderRepository seckillOrderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitOrder(Long seckillId, String userId) {
        //从缓存中查询秒杀商品
        SeckillGoods seckillGoods = seckillGoodsService.findById(seckillId);
        //库存
        Integer stockCount = seckillGoods.getStockCount();

        if (seckillGoods == null) {
            log.error("商品不存在 = {}", seckillGoods);
            throw new RuntimeException("商品不存在");
        }
        if (stockCount <= 0) {
            throw new RuntimeException("商品库存不够");
        }
        //扣减（redis）库存
        seckillGoods.setStockCount(stockCount - 1);
        //放回缓存
        redisTemplate.boundHashOps(SeckillGoodsService.SECKILL_GOODS_KEY).put(seckillId, seckillGoods);

        //如果已经被秒光
        if (seckillGoods.getStockCount() == 0) {
            //同步到数据库
            boolean flag = seckillGoodsService.emptySeckillGood(seckillId);
            if (flag) {
                redisTemplate.boundHashOps(SeckillGoodsService.SECKILL_GOODS_KEY).delete(seckillId);
            } else {
                log.error("发生未知错误，在向数据库清空商品的对应库存的时候，商品id={}", seckillId);
                return;
            }
        }

        //保存订单到redis,支付成功在存在数据库
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setCreateTime(new Date());
        //秒杀价格
        seckillOrder.setMoney(seckillGoods.getCostPrice());
        seckillOrder.setSeckillId(seckillId);
        seckillOrder.setSellerId(seckillGoods.getSellerId());
        //设置用户ID
        seckillOrder.setUserId(userId);
        //状态
        seckillOrder.setStatus("0");
        redisTemplate.boundHashOps(SECKILL_ORDER_KEY).put(userId, seckillOrder);
    }

    @Override
    public SeckillOrder searchOrderFromRedisByUserId(String userId) {
        return (SeckillOrder) redisTemplate.boundHashOps(SECKILL_ORDER_KEY).get(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderFromRedisToDb(String userId, Long seckillId, String transactionId) {
        SeckillOrder seckillOrder = searchOrderFromRedisByUserId(userId);
        if (seckillOrder == null) {
            log.error("根据用户id查询该用户的订单为空={}", userId);
            return;
        }
        if (!seckillOrder.getId().equals(seckillId)) {
            log.error("用户传过来的订单号跟查询出来的订单不符合,订单号={},查询出来的订单信息={}", seckillId, seckillOrder);
            return;
        }
        //交易流水号
        seckillOrder.setTransactionId(transactionId);
        seckillOrder.setPayTime(new Date());
        //支付时间
        //状态 已支付
        seckillOrder.setStatus("1");

        System.err.println(seckillOrder);
        //保存到数据库
        seckillOrderRepository.save(seckillOrder);

        //从redis中清除
        redisTemplate.boundHashOps(SECKILL_ORDER_KEY).delete(userId);
    }

    @Override
    public void deleteOrderFromRedis(String userId, Long orderId) {
        //根据用户ID查询订单
        SeckillOrder seckillOrder = searchOrderFromRedisByUserId(userId);
        if (seckillOrder == null || !orderId.equals(seckillOrder.getId())) {
            log.error("对应的订单不成立!!");
            return;
        } else {
            //删除缓存中的订单
            redisTemplate.boundHashOps(SeckillOrderService.SECKILL_ORDER_KEY).delete(userId);
            //商品id
            Long seckillId = seckillOrder.getSeckillId();
            //恢复库存
            //1.从缓存中提取秒杀商品
            SeckillGoods seckillGoods = seckillGoodsService.findById(seckillId);
            if (seckillGoods != null) {
                seckillGoods.setStockCount(seckillGoods.getStockCount() + 1);
                //存入缓存
                redisTemplate.boundHashOps(SeckillGoodsService.SECKILL_GOODS_KEY).put(seckillId, seckillGoods);
            } else {
                //等于空代表缓存中的商品库存为零，被清除了，需要重新从数据库查询放入缓存
                seckillGoods = seckillGoodsService.findByIdInDb(seckillId);
                if (seckillGoods != null) {
                    //数量为1
                    seckillGoods.setStockCount(1);
                    //更新到数据库
                    seckillGoodsService.addStockCountOne(seckillGoods);
                    //存到缓存中
                    redisTemplate.boundHashOps(SeckillGoodsService.SECKILL_GOODS_KEY).put(seckillId, seckillGoods);
                } else {
                    log.error("根据id到商品表查询返回为空={}", seckillGoods);
                }
            }
        }
    }
}
