package com.haru.mybatis.pattern.abstarcted;

import com.haru.mybatis.pattern.Shape;

import java.awt.*;

/**
 * @author HARU
 * @since 2018/10/3
 */
public interface ShapeColorFactory {
    Shape createShape();
    Color createColor();
}
