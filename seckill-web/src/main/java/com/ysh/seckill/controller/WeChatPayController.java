package com.ysh.seckill.controller;

import com.ysh.seckill.common.ResponseData;
import com.ysh.seckill.entity.SeckillOrder;
import com.ysh.seckill.service.SeckillOrderService;
import com.ysh.seckill.service.WeChatPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author joey
 * @date 2018/06/02 17:22
 */
@RequestMapping("/pay")
@RestController
@Slf4j
public class WeChatPayController {

    @Autowired
    private WeChatPayService weChatPayService;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/createNative")
    public Map createNative(String userId) {
        if (StringUtils.isEmpty(userId)) {
            log.error("用户id为空");
            return null;
        }
        //1.获取当前登录用户
        //2.提取秒杀订单（从缓存 ）
        SeckillOrder seckillOrder = seckillOrderService.searchOrderFromRedisByUserId(userId);
        //3.调用微信支付接口
        if (seckillOrder != null) {
            return weChatPayService.createNative(seckillOrder.getId() + "",
                    (long) (seckillOrder.getMoney().doubleValue() * 100) + "");
        } else {
            log.error("seckillOrder is empty = {}", seckillOrder);
            return new HashMap<>();
        }
    }

    @RequestMapping("/queryPayStatus")
    public ResponseData queryPayStatus(String out_trade_no, String userId) {
        ResponseData result = null;
        int x = 0;
        while (true) {
            //调用查询
            // Map<String, String> map = weChatPayService.queryPayStatus(out_trade_no);
            Map<String, String> map = new HashMap<>();
            map.put("transaction_id", "1233444");
            map.put("trade_state", "fa");

            if (map == null) {
                result = new ResponseData("支付发生错误", false);
                break;
            }
            if (x == 2) {
                map.put("trade_state", "SUCCESS");
            }
            //支付成功
            if (map.get("trade_state").equals("SUCCESS")) {
                result = new ResponseData("支付成功", true);
                //保存订单
                seckillOrderService.saveOrderFromRedisToDb(userId, Long.valueOf(out_trade_no), map.get("transaction_id"));
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
            if (x > 100) {

                result = new ResponseData("二维码超时", false);

                // 关闭支付
                Map<String, String> payResult = weChatPayService.closePay(out_trade_no);
                //当微信支付关闭订单的时候，有可能会出错（关闭的时候，用户已经支付）
                if (payResult != null && "FAIL".equals(payResult.get("return_code"))) {
                    //用户支付，正常逻辑
                    if ("ORDERPAID".equals(payResult.get("err_code"))) {
                        result = new ResponseData("支付成功", true);
                        //保存订单
                        seckillOrderService.saveOrderFromRedisToDb(userId, Long.valueOf(out_trade_no), map.get("transaction_id"));
                    }
                }
                //删除订单(代表用户没支付的情况下超时)
                if (result.isFlag() == false) {
                    seckillOrderService.deleteOrderFromRedis(userId, Long.valueOf(out_trade_no));
                }
                break;
            }
        }
        return result;
    }
}
