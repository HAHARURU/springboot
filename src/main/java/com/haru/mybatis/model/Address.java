package com.haru.mybatis.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 地址
 *
 * @author HARU
 * @date 2019/5/4
 **/
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 9075663601190785356L;

    /**
     * 地址id
     */
    private Integer id;

    /**
     * 城市
     */
    private String city;

}
