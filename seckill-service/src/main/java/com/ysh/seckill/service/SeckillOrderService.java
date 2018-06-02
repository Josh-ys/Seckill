package com.ysh.seckill.service;

import com.ysh.seckill.entity.SeckillOrder;

/**
 * @author joey
 * @date 2018/05/30 00:22
 */
public interface SeckillOrderService {

    String SECKILL_ORDER_KEY = "seckillOrder";

    /**
     * 提交订单
     *
     * @param seckillId
     * @param userId
     */
    void submitOrder(Long seckillId, String userId);

    /**
     * 根据用户名查询秒杀订单
     *
     * @param userId
     * @return
     */
    SeckillOrder searchOrderFromRedisByUserId(String userId);

    /**
     * 支付成功，把订单从redis保存到数据库并把该条记录从缓存中删除
     *
     * @param userId
     * @param seckillId
     * @param transactionId
     */
    void saveOrderFromRedisToDb(String userId, Long seckillId, String transactionId);

    /**
     * 从缓存中删除订单
     *
     * @param userId
     * @param orderId
     */
    void deleteOrderFromRedis(String userId, Long orderId);
}
