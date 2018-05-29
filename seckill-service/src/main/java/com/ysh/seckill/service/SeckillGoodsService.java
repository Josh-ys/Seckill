package com.ysh.seckill.service;

import com.ysh.seckill.common.PageEntity;
import com.ysh.seckill.entity.SeckillGoods;
import org.springframework.stereotype.Service;

/**
 * @author joey
 * @date 2018/05/30 00:21
 */
@Service
public interface SeckillGoodsService {
    /**
     * 带条件的分页查询
     * 查询正在秒杀的商品列表
     *
     * @param start
     * @param limit
     * @return
     */
    PageEntity<SeckillGoods> pageQuery(Integer start, Integer limit);
}
