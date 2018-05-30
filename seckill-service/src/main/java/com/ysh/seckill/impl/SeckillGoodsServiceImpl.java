package com.ysh.seckill.impl;

import com.ysh.seckill.common.PageEntity;
import com.ysh.seckill.dao.SeckillGoodsRepository;
import com.ysh.seckill.entity.SeckillGoods;
import com.ysh.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ysh.seckill.common.PageEntity.createBuilder;

/**
 * @author joey
 * @date 2018/05/30 00:21
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private SeckillGoodsRepository seckillGoodsRepository;

    @Override
    public PageEntity<SeckillGoods> pageQuery(Integer start, Integer limit) {
        // 封装Pageable对象
        PageRequest pageable = PageRequest.of(start - 1, limit);
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
        Page<SeckillGoods> goods = seckillGoodsRepository.findAll(specification, pageable);
        return createBuilder().withDataList(goods.getContent()).withTotalCount(goods.getTotalElements()).builder();
    }
}
