package com.haru.mybatis.pattern.simple;

import com.haru.mybatis.pattern.Circle;
import com.haru.mybatis.pattern.Shape;
import com.haru.mybatis.pattern.Triangle;

import java.util.Optional;

/**
 * @author HARU
 * @since 2018/9/30
 */
public class ShapeFactory {
    public static String CIRCLE = "circle";
    public static String TRIANGLE = "triangle";

    public static Shape createShape(String name) {
        Shape shape = null;
        Optional<String> nameOption = Optional.ofNullable(name);
        if (nameOption.isPresent()) {
            switch (nameOption.get()) {
                case "circle":
                    shape = new Circle();
                    break;
                case "triangle":
                    shape = new Triangle();
                    break;
                default:
                    break;
            }
        }
        return shape;
    }
}
