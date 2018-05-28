package com.ysh.seckill.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author joey
 * @date 2018/05/28 23:44
 */
@RequestMapping("/first")
@RestController
public class firstController {

    @RequestMapping("/test")
    public String first() {
        String s = "hello world1";
        return s;
    }
}
