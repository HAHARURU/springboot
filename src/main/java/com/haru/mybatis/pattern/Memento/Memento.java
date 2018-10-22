package com.haru.mybatis.pattern.Memento;

/**
 * @author HARU
 * @since 2018/10/22
 */
public class Memento {
    private String value;

    public Memento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
