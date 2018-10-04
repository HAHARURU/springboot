package com.haru.mybatis.pattern.abstarcted;

import com.haru.mybatis.pattern.Circle;
import com.haru.mybatis.pattern.Shape;

/**
 * @author HARU
 * @since 2018/10/3
 */
public class RedCircleFactory implements ShapeColorFactory {
    @Override
    public Shape createShape() {
        return new Circle();
    }

    @Override
    public Color createColor() {
        return new Red();
    }
}
