package com.haru.mybatis.pattern.prototype;

import java.io.Serializable;

/**
 * @author HARU
 * @since 2018/10/6
 */
public class Driver implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
