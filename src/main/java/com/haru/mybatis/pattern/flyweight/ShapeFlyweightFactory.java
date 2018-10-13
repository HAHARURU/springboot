package com.haru.mybatis.pattern.flyweight;

import com.haru.mybatis.pattern.Circle;
import com.haru.mybatis.pattern.Shape;
import com.haru.mybatis.pattern.Triangle;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author HARU
 * @since 2018/10/13
 */
public class ShapeFlyweightFactory {
    private static final HashMap<String, Shape> shapeMap = new HashMap<>();    //对象池
    public static String CIRCLE = "circle";
    public static String TRIANGLE = "triangle";

    public static Shape createShape(String name) {
        Shape shape = shapeMap.get(name);
        Optional<Shape> shapeOption = Optional.ofNullable(shape);
        if (!shapeOption.isPresent()) {
            System.out.println("new shape");
            switch (name) {
                case "circle":
                    shape = new Circle();
                    break;
                case "triangle":
                    shape = new Triangle();
                    break;
                default:
                    break;
            }
            shapeMap.put(name, shape);
        } else {
            System.out.println("get shape");
        }
        return shape;
    }
}
