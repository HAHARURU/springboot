package com.haru.mybatis.pattern.bridge;

/**
 * @author HARU
 * @since 2018/10/10
 */
public abstract class Shape {
    Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public abstract void draw();
}
