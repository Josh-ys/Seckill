package com.ysh.seckill.impl;

import com.ysh.seckill.dao.SeckillGoodsRepository;
import com.ysh.seckill.entity.SeckillGoods;
import com.ysh.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author joey
 * @date 2018/05/30 00:21
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsRepository seckillGoodsRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> findAll() {
        List<SeckillGoods> seckillGoods = redisTemplate.boundHashOps("seckillGoods").values();
        if (seckillGoods == null || seckillGoods.size() == 0) {
            // 封装Pageable对象
            Specification<SeckillGoods> specification = new Specification<SeckillGoods>() {
                /**
                 * 构造条件查询方法，如果方法返回null。代表无条件查询。 Root： 参数获取条件表达式 name=？ ，age = ？用于获取属性字段。(查询的对象)
                 * CriteriaQuery：可以用于简单条件查询，提供where方法。 CriteriaBuilder：
                 * 用于构造复杂条件查询,参数构造Predicate对象，条件对象，构造复杂查询效果。
                 */
                @Override
                public Predicate toPredicate(Root<SeckillGoods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> seckillGoods = new ArrayList<>();
                    //状态为审核通过
                    seckillGoods.add(criteriaBuilder.equal(root.get("status").as(String.class), "1"));
                    //库存大于0
                    seckillGoods.add(criteriaBuilder.greaterThan(root.get("stockCount").as(Integer.class), 0));
                    //开始时间小于等于当前时间
                    seckillGoods.add(criteriaBuilder.lessThanOrEqualTo(root.get("startTime").as(Date.class), new Date()));
                    //结束时间大于等于当前时间
                    seckillGoods.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endTime").as(Date.class), new Date()));
                    return criteriaBuilder.and(seckillGoods.toArray(new Predicate[0]));
                }
            };
            seckillGoods = seckillGoodsRepository.findAll(specification);
            //将商品列表装入缓存
            System.out.println("将秒杀商品列表装入缓存");
            for (SeckillGoods goods : seckillGoods) {
                redisTemplate.boundHashOps("seckillGoods").put(goods.getId(), goods);
            }
        } else {
            System.err.println("缓存读取");
        }
        return seckillGoods;
    }

    @Override
    public SeckillGoods findById(Long id) {
        return (SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(id);
    }
}
