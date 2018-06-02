package com.ysh.seckill.impl;

import com.ysh.seckill.dao.SeckillGoodsRepository;
import com.ysh.seckill.entity.SeckillGoods;
import com.ysh.seckill.service.SeckillGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author joey
 * @date 2018/05/30 00:21
 */
@Service
@Slf4j
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsRepository seckillGoodsRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SeckillGoods> findAll() {
        List<SeckillGoods> seckillGoods = redisTemplate.boundHashOps(SECKILL_GOODS_KEY).values();
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
            log.info("将秒杀商品列表装入缓存");
            for (SeckillGoods goods : seckillGoods) {
                redisTemplate.boundHashOps(SECKILL_GOODS_KEY).put(goods.getId(), goods);
            }
        } else {
            log.info("缓存读取");
        }
        return seckillGoods;
    }

    @Override
    public SeckillGoods findById(Long id) {
        return (SeckillGoods) redisTemplate.boundHashOps(SECKILL_GOODS_KEY).get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reduceNumber(Long id, Date killTime) {
        return seckillGoodsRepository.reduceNumber(id, killTime) > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean emptySeckillGood(Long id) {
        return seckillGoodsRepository.emptySeckillGood(id) > 0 ? true : false;
    }

    @Override
    public SeckillGoods findByIdInDb(Long id) {
        if (id == null || id <= 0) {
            log.error("从数据库查询的商品，传进来的商品id为空={}", id);
            return null;
        }
        Optional<SeckillGoods> goods = seckillGoodsRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("id").as(Long.class), id));
            //状态为审核通过
            predicates.add(criteriaBuilder.equal(root.get("status").as(String.class), "1"));
            //开始时间小于等于当前时间
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startTime").as(Date.class), new Date()));
            //结束时间大于等于当前时间
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endTime").as(Date.class), new Date()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return goods.isPresent() ? goods.get() : null;
    }

    @Override
    public void addStockCountOne(SeckillGoods seckillGoods) {
        seckillGoodsRepository.save(seckillGoods);
    }
}
