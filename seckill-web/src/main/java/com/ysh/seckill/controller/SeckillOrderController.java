package com.ysh.seckill.controller;

import com.ysh.seckill.common.ResponseData;
import com.ysh.seckill.service.SeckillOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author joey
 * @date 2018/05/30 00:24
 */
@RestController
@RequestMapping("/seckillOrder")
@Slf4j
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @RequestMapping("/submitOrder")
    public ResponseData submitOrder(Long id, String userId) {
        if (StringUtils.isEmpty(userId) || id == null) {
            return new ResponseData("参数异常", false);
        }
        try {
            seckillOrderService.submitOrder(id, userId);
            return new ResponseData("提交成功!!", true);
        } catch (Exception e) {
            log.error("订单提交失败 = {}", e.getMessage());
            return new ResponseData("提交失败!!", false);
        }
    }
}
