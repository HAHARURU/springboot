package com.haru.mybatis;

import com.haru.mybatis.pattern.Shape;
import com.haru.mybatis.pattern.factory.CircleFactory;
import com.haru.mybatis.pattern.factory.TriangleFactory;
import com.haru.mybatis.pattern.simple.ShapeFactory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author HARU
 * @since 2018/9/30
 */
@SpringBootTest
public class PatternTest {

    @Test
    public void staticFactoryTest() {
        Shape circle = ShapeFactory.createShape(ShapeFactory.CIRCLE);
        circle.draw();
        Shape triangle = ShapeFactory.createShape(ShapeFactory.TRIANGLE);
        triangle.draw();
    }

        @Test
        public void factoryTest() {
            com.haru.mybatis.pattern.factory.ShapeFactory circleFactory = new CircleFactory();
            Shape circle = circleFactory.createShape();
            circle.draw();
            com.haru.mybatis.pattern.factory.ShapeFactory triangleFactory = new TriangleFactory();
            Shape triangle = triangleFactory.createShape();
            triangle.draw();
        }
}
