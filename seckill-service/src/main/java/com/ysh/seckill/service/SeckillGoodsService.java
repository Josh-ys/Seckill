package com.ysh.seckill.service;

import com.ysh.seckill.entity.SeckillGoods;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return
     */
    List<SeckillGoods> findAll();

    /**
     * 查询商品详情
     * 从缓冲中
     *
     * @param id
     * @return
     */
    SeckillGoods findById(Long id);
}
