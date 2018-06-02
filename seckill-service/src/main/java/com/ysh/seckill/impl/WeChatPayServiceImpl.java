package com.ysh.seckill.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.ysh.seckill.common.HttpClient;
import com.ysh.seckill.service.WeChatPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author joey
 * @date 2018/06/02 17:05
 */
@Slf4j
@Service
public class WeChatPayServiceImpl implements WeChatPayService {

    @Value("${weChat.Pay.appId}")
    private String appId;

    @Value("${weChat.Pay.partner}")
    private String partner;

    @Value("${weChat.Pay.partnerKey}")
    private String partnerKey;

    @Value("${weChat.Pay.notifyUrl}")
    private String notifyUrl;

    @Override
    public Map createNative(String out_trade_no, String total_fee) {
        //1.参数封装
        Map param = new HashMap();
        //公众账号ID
        param.put("appid", appId);
        //商户
        param.put("mch_id", partner);
        //随机字符串
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        //商品描述
        param.put("body", "秒杀商品");
        //交易订单号
        param.put("out_trade_no", out_trade_no);
        //金额（分）
        param.put("total_fee", total_fee);
        //IP
        param.put("spbill_create_ip", "127.0.0.1");
        //回调地址(随便写)
        param.put("notify_url", notifyUrl);
        //交易类型
        param.put("trade_type", "NATIVE");
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerKey);
            log.info("请求的参数 = {}", xmlParam);

            //2.发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();

            //3.获取结果
            String xmlResult = httpClient.getContent();

            Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);
            Map map = new HashMap<>();
            //生成支付二维码的链接
            map.put("code_url", mapResult.get("code_url"));
            map.put("out_trade_no", out_trade_no);
            map.put("total_fee", total_fee);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成支付地址错误 = {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Map queryPayStatus(String out_trade_no) {
        //1.封装参数
        Map param = new HashMap();
        param.put("appid", appId);
        param.put("mch_id", partner);
        param.put("out_trade_no", out_trade_no);
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerKey);
            //2.发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();

            //3.获取结果
            String xmlResult = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            log.info("调动查询API返回结果 = {}", xmlParam);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询支付订单状态出错 = {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Map closePay(String out_trade_no) {
        //1.封装参数
        Map param = new HashMap();
        param.put("appid", appId);
        param.put("mch_id", partner);
        param.put("out_trade_no", out_trade_no);
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerKey);
            //2.发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3.获取结果
            String xmlResult = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            log.info("调动查询API返回结果 = {}", xmlParam);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("关闭订单出错 = {}", e.getMessage());
            return null;
        }
    }
}
