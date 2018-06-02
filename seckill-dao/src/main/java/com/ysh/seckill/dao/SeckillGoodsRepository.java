package com.ysh.seckill.dao;

import com.ysh.seckill.entity.SeckillGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

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

    /**
     * 减库存
     *
     * @param id
     * @param killTime
     * @return
     */
    @Modifying
    @Query("update SeckillGoods set stockCount=stockCount-1 " +
            "where id=:id and startTime<=:killTime and endTime>=:killTime and stockCount > 0")
    int reduceNumber(@Param("id") Long id, @Param("killTime") Date killTime);

    /**
     * 清空对应商品库存
     *
     * @param id
     * @return
     */
    @Modifying
    @Query("update SeckillGoods set stockCount=0 where id = :id")
    int emptySeckillGood(@Param("id") Long id);
}
