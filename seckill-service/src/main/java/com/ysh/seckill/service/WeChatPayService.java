package com.ysh.seckill.service;

import java.util.Map;

/**
 * 微信支付接口
 *
 * @author joey
 * @date 2018/06/02 17:02
 */
public interface WeChatPayService {

    /**
     * 生成微信支付二维码
     *
     * @param out_trade_no 订单号
     * @param total_fee    金额
     * @return
     */
    Map createNative(String out_trade_no, String total_fee);

    /**
     * 查询支付订单状态
     *
     * @param out_trade_no
     * @return
     */
    Map queryPayStatus(String out_trade_no);


    /**
     * 关闭订单
     *
     * @param out_trade_no
     * @return
     */
    Map closePay(String out_trade_no);
}
