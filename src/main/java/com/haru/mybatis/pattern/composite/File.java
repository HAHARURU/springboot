package com.haru.mybatis.pattern.composite;

/**
 * @author HARU
 * @since 2018/10/12
 */
public abstract class File {
    String name;

    public File(String name) {
        this.name = name;
    }

    public abstract void show();
}
