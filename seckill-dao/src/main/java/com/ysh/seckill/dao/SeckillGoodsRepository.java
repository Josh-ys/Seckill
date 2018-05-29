package com.ysh.seckill.dao;

import com.ysh.seckill.entity.SeckillGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaSpecificationExecutor:带条件的分页查询
 * Page<T> findAll(Specification<T> spec, Pageable pageable);
 * Specification 接口对象 代表条件查询对象
 * Pageable 接口对象 代表分页查询对象
 *
 * @author joey
 * @date 2018/05/30 00:19
 */
public interface SeckillGoodsRepository extends JpaRepository<SeckillGoods, Long>, JpaSpecificationExecutor<SeckillGoods> {
}
