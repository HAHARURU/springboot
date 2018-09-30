package com.haru.mybatis;

import com.haru.mybatis.pattern.Shape;
import com.haru.mybatis.pattern.ShapeFactory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author HARU
 * @since 2018/9/30
 */
@SpringBootTest
public class PatternTest {

    @Test
    public void createShapeTest() {
        Shape circle = ShapeFactory.createShape(ShapeFactory.CIRCLE);
        circle.draw();
        Shape triangle = ShapeFactory.createShape(ShapeFactory.TRIANGLE);
        triangle.draw();
    }
}
