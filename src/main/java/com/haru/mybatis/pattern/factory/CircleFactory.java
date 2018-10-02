package com.haru.mybatis.pattern.factory;

import com.haru.mybatis.pattern.Circle;
import com.haru.mybatis.pattern.Shape;

/**
 * @author HARU
 * @since 2018/10/2
 */
public class CircleFactory implements ShapeFactory{
    @Override
    public Shape createShape() {
        return new Circle();
    }
}
