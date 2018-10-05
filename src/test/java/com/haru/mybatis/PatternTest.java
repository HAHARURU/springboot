package com.haru.mybatis;

import com.haru.mybatis.pattern.Shape;
import com.haru.mybatis.pattern.abstarcted.Color;
import com.haru.mybatis.pattern.abstarcted.RedCircleFactory;
import com.haru.mybatis.pattern.abstarcted.ShapeColorFactory;
import com.haru.mybatis.pattern.builder.Actor;
import com.haru.mybatis.pattern.builder.ActorBuilder;
import com.haru.mybatis.pattern.builder.ActorDirector;
import com.haru.mybatis.pattern.builder.SaberBuilder;
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

    @Test
    public void abstractTest() {
        ShapeColorFactory redCircleFactory  = new RedCircleFactory();
        Color red = redCircleFactory.createColor();
        Shape circle = redCircleFactory.createShape();
        red.paint();
        circle.draw();
    }

    @Test
    public void builderTest() {
        ActorBuilder saberActor = new SaberBuilder();
        ActorDirector actorDirector = new ActorDirector();
        Actor saber = actorDirector.createActor(saberActor);
        System.out.println(saber.toString());
    }
}
