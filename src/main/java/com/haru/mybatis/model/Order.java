package com.haru.mybatis.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 订单
 *
 * @author HARU
 * @date 2019/5/4
 **/
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -5856859005996624490L;

    /**
     * 订单id
     */
    private Integer id;

    /**
     * 订单
     */
    private String orderCode;

    /**
     * 地址
     */
    private Address address;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 创建日期
     */
    private Date crateDate;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 商品
     */
    private List<Item> items;
}