package com.haru.mybatis.generation.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * freemarker模板中的fields对象
 *
 * @author HARU
 * @date 2019/5/5
 **/
@Data
public class Field implements Serializable {

    private static final long serialVersionUID = 5630211480080294875L;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段注释
     */
    private String comments;
}
