package com.haru.mybatis.model;

import java.io.Serializable;

/**
 * @author HARU
 * @since 2018/7/10
 */
public class Visitor extends BaseEntity implements Serializable {
    /**
     * 名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
