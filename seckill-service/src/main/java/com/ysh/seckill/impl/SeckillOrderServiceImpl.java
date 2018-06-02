package com.ysh.seckill.impl;

import com.ysh.seckill.common.IdWorker;
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
}
