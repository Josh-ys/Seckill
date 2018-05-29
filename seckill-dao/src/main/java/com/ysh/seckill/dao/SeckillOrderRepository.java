package com.ysh.seckill.dao;

import com.ysh.seckill.entity.SeckillOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author joey
 * @date 2018/05/30 00:20
 */
public interface SeckillOrderRepository extends JpaRepository<SeckillOrder, Long>, JpaSpecificationExecutor<SeckillOrder> {
}
