package com.haru.mybatis.pattern.bridge;

/**
 * @author HARU
 * @since 2018/10/10
 */
public class Circle extends Shape {
    public Circle(Color color) {
        super(color);
    }

    @Override
    public void draw() {
        System.out.println("draw circle");
        color.paint();
    }
}
