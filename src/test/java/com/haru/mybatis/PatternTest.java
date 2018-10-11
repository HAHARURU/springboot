package com.haru.mybatis;

import com.haru.mybatis.pattern.Shape;
import com.haru.mybatis.pattern.abstarcted.Color;
import com.haru.mybatis.pattern.abstarcted.RedCircleFactory;
import com.haru.mybatis.pattern.abstarcted.ShapeColorFactory;
import com.haru.mybatis.pattern.adapter.*;
import com.haru.mybatis.pattern.bridge.Circle;
import com.haru.mybatis.pattern.bridge.Red;
import com.haru.mybatis.pattern.builder.Actor;
import com.haru.mybatis.pattern.builder.ActorBuilder;
import com.haru.mybatis.pattern.builder.ActorDirector;
import com.haru.mybatis.pattern.builder.SaberBuilder;
import com.haru.mybatis.pattern.decorator.Benz;
import com.haru.mybatis.pattern.decorator.TailCarDecorator;
import com.haru.mybatis.pattern.facade.Facade;
import com.haru.mybatis.pattern.factory.CircleFactory;
import com.haru.mybatis.pattern.factory.TriangleFactory;
import com.haru.mybatis.pattern.prototype.Attachment;
import com.haru.mybatis.pattern.prototype.Car;
import com.haru.mybatis.pattern.prototype.Driver;
import com.haru.mybatis.pattern.prototype.Order;
import com.haru.mybatis.pattern.proxy.*;
import com.haru.mybatis.pattern.simple.ShapeFactory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Proxy;

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
        ShapeColorFactory redCircleFactory = new RedCircleFactory();
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

    @Test
    public void prototypeTest() {
        Order oldOrder = new Order();
        Attachment attachment = new Attachment();
        oldOrder.setAttachment(attachment);
        Order cloneOrder = oldOrder.clone();
        System.out.println("order是否相同：" + (oldOrder == cloneOrder));
        System.out.println("attachment是否相同：" + (oldOrder.getAttachment() == cloneOrder.getAttachment()));
    }

    @Test
    public void deepCloneTest() {
        Driver driver = new Driver();
        Car car = new Car();
        car.setDriver(driver);
        Car cloneCar = car.clone();
        System.out.println("car是否相同：" + (car == cloneCar));
        System.out.println("driver是否相同：" + (car.getDriver() == cloneCar.getDriver()));
    }

    @Test
    public void decoratorTest() {
        com.haru.mybatis.pattern.decorator.Car benz = new Benz();
        TailCarDecorator tailCarDecorator = new TailCarDecorator(benz);
        tailCarDecorator.make();
    }

    @Test
    public void classAdapterTest() {
        DC5 voltageAdapter = new VoltageAdapter();
        voltageAdapter.charge5V();
    }

    @Test
    public void objectAdapterTest() {
        ObjectVoltageAdapter objectVoltageAdapter = new ObjectVoltageAdapter(new AC220());
        objectVoltageAdapter.charge5V();
    }

    @Test
    public void interfaceAdapterTest() {
        Voltage5VAdapter voltage5VAdapter = new Voltage5VAdapter(new AC220());
        voltage5VAdapter.charge5V();

        InterfaceVoltageAdapter interfaceVoltageAdapter = new InterfaceVoltageAdapter(new AC220()) {
            @Override
            public void charge9V() {
                sourceVoltage.charge();
                System.out.println("change DC9V");
            }
        };
        interfaceVoltageAdapter.charge9V();
    }

    @Test
    public void proxyTest() {
        HouseProxy houseProxy = new HouseProxy(new HouseImpl());
        houseProxy.buy();
    }

    @Test
    public void DynamicProxyTest() {
        House house = (House) Proxy.newProxyInstance(House.class.getClassLoader(), new Class[]{House.class}, new
                DynamicProxyHandler(new HouseImpl()));
        house.buy();
    }

    @Test
    public void CglibProxyTest() {
        CglibProxy cglibProxy = new CglibProxy();
        com.haru.mybatis.pattern.proxy.Car car = (com.haru.mybatis.pattern.proxy.Car) cglibProxy.getInstance(com
                .haru.mybatis.pattern.proxy.Car.class);
        car.buy();
    }

    @Test
    public void BridgeTest() {
        com.haru.mybatis.pattern.bridge.Shape circle = new Circle(new Red());
        circle.draw();
    }

    @Test
    public void FacadeTest() {
        Facade facade = new Facade();
        facade.run();
    }
}
