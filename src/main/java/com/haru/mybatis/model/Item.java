package com.haru.mybatis.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 货物
 *
 * @author HARU
 * @date 2019/5/4
 **/
@Data
public class Item implements Serializable {

    private static final long serialVersionUID = -2517810372041972770L;

    /**
     * 货物id
     */
    private Integer id;

    /**
     * 货物名称
     */
    private String name;

}
