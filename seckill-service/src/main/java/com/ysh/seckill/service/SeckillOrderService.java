package com.ysh.seckill.service;

/**
 * @author joey
 * @date 2018/05/30 00:22
 */
public interface SeckillOrderService {

    String SECKILL_ORDER_KEY = "seckillOrder";
    /**
     * 提交订单
     * @param seckillId
     * @param userId
     */
    void submitOrder(Long seckillId, String userId);
}
