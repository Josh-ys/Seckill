package com.ysh.seckill.service;

import com.ysh.seckill.entity.SeckillGoods;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author joey
 * @date 2018/05/30 00:21
 */
@Service
public interface SeckillGoodsService {

    /**
     * 存到redis中的商品的大key
     */
    String SECKILL_GOODS_KEY = "seckillGoods";

    /**
     * 查询正在秒杀的商品列表
     *
     * @return
     */
    List<SeckillGoods> findAll();

    /**
     * 查询商品详情
     * 从缓存中
     *
     * @param id
     * @return
     */
    SeckillGoods findById(Long id);

    /**
     * 减库存
     *
     * @param id
     * @param killTime
     * @return
     */
    boolean reduceNumber(Long id, Date killTime);

    /**
     * 清空库存
     *
     * @param id
     * @return
     */
    boolean emptySeckillGood(Long id);

    /**
     * 从数据库查询
     *
     * @param id
     * @return
     */
    SeckillGoods findByIdInDb(Long id);

    /**
     * 更新数据库
     * @param seckillGoods
     */
    void addStockCountOne(SeckillGoods seckillGoods);
}
