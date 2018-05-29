package com.ysh.seckill.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author joey
 * @date 2018/05/29 23:55
 */
@Entity
@Table(name = "t_seckill_goods")
@Data
public class SeckillGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * spu ID
     */
    @Column(name = "goods_id")
    private Long goodsId;
    /**
     * sku ID
     */
    @Column(name = "item_id")
    private Long itemId;
    /**
     * 标题
     */
    @Column
    private String title;
    /**
     * 商品图片
     */
    @Column(name = "small_pic")
    private String smallPic;
    /**
     * 原价格
     */
    @Column
    private BigDecimal price;
    /**
     * 秒杀价格
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;
    /**
     * 商家ID
     */
    @Column(name = "seller_id")
    private String sellerId;
    /**
     * 添加日期
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 审核日期
     */
    @Column(name = "check_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkTime;
    /**
     * 审核状态
     */
    @Column
    private String status;
    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 秒杀商品数
     */
    @Column
    private Integer num;
    /**
     * 剩余库存数
     */
    @Column(name = "stock_count")
    private Integer stockCount;
    /**
     * 描述
     */
    @Column
    private String introduction;
}
