package com.ysh.seckill.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author joey
 * @date 2018/06/02 00:41
 */
@Data
@AllArgsConstructor
public class ResponseData {
    private String respMsg;
    private boolean flag;
}
