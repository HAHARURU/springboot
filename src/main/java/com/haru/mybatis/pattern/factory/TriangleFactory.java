package com.haru.mybatis.pattern.factory;

import com.haru.mybatis.pattern.Shape;
import com.haru.mybatis.pattern.Triangle;

/**
 * @author HARU
 * @since 2018/10/2
 */
public class TriangleFactory implements ShapeFactory {
    @Override
    public Shape createShape() {
        return new Triangle();
    }
}
