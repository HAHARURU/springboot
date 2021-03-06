package com.haru.mybatis;

import com.haru.mybatis.pattern.memento.Memento;
import com.haru.mybatis.pattern.memento.MementoManagement;
import com.haru.mybatis.pattern.memento.Origin;
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
import com.haru.mybatis.pattern.chain.LetterHandler;
import com.haru.mybatis.pattern.chain.NumberHandler;
import com.haru.mybatis.pattern.chain.SpecialCharacterHandler;
import com.haru.mybatis.pattern.chain.StringHandler;
import com.haru.mybatis.pattern.command.*;
import com.haru.mybatis.pattern.composite.Folder;
import com.haru.mybatis.pattern.composite.ImageFile;
import com.haru.mybatis.pattern.composite.VideoFile;
import com.haru.mybatis.pattern.decorator.Benz;
import com.haru.mybatis.pattern.decorator.TailCarDecorator;
import com.haru.mybatis.pattern.facade.Facade;
import com.haru.mybatis.pattern.factory.CircleFactory;
import com.haru.mybatis.pattern.factory.TriangleFactory;
import com.haru.mybatis.pattern.flyweight.ShapeFlyweightFactory;
import com.haru.mybatis.pattern.iterator.white.FruitAggregate;
import com.haru.mybatis.pattern.iterator.white.Iterator;
import com.haru.mybatis.pattern.mediator.ChatMediator;
import com.haru.mybatis.pattern.mediator.ChatUserA;
import com.haru.mybatis.pattern.mediator.ChatUserB;
import com.haru.mybatis.pattern.observer.Student;
import com.haru.mybatis.pattern.observer.Weather;
import com.haru.mybatis.pattern.observer.Worker;
import com.haru.mybatis.pattern.prototype.Attachment;
import com.haru.mybatis.pattern.prototype.Car;
import com.haru.mybatis.pattern.prototype.Driver;
import com.haru.mybatis.pattern.prototype.Order;
import com.haru.mybatis.pattern.proxy.*;
import com.haru.mybatis.pattern.simple.ShapeFactory;
import com.haru.mybatis.pattern.state.Context;
import com.haru.mybatis.pattern.strategy.AddMethod;
import com.haru.mybatis.pattern.strategy.MathContext;
import com.haru.mybatis.pattern.strategy.SubtractMethod;
import com.haru.mybatis.pattern.template.Dinner;
import com.haru.mybatis.pattern.template.Kelp;
import com.haru.mybatis.pattern.template.Potato;
import com.haru.mybatis.pattern.visitor.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void StrategyTest() {
        MathContext add = new MathContext(new AddMethod());
        add.run();
        MathContext subtract = new MathContext(new SubtractMethod());
        subtract.run();
    }

    @Test
    public void CompositeTest() {
        Folder folder = new Folder("admin");
        folder.add(new ImageFile("logo"));
        folder.add(new VideoFile("MV"));
        folder.show();
    }

    @Test
    public void FlyweightTest() {
        Shape circleNew = ShapeFlyweightFactory.createShape(ShapeFlyweightFactory.CIRCLE);
        circleNew.draw();
        Shape circleGet = ShapeFlyweightFactory.createShape(ShapeFlyweightFactory.CIRCLE);
        circleGet.draw();
    }

    @Test
    public void TemplateTest() {
        Dinner potato = new Potato();
        potato.mark();
        Dinner kelp = new Kelp();
        kelp.mark();
    }

    @Test
    public void ObserverTest() {
        Weather weather = new Weather();
        Worker worker = new Worker();
        Student student = new Student();
        weather.registerObserver(worker);
        weather.registerObserver(student);
        weather.setMessage("rain message");
        weather.removeObserver(worker);
        weather.setMessage("snow message");
    }

    @Test
    public void WhiteIteratorTest() {
        String[] fruitArray = {"apple", "banana", "strawberry"};
        List<String> fruits = new ArrayList<String>(Arrays.asList(fruitArray));
        FruitAggregate fruitAggregate = new FruitAggregate(fruits);
        Iterator iterator = fruitAggregate.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void BlackIteratorTest() {
        String[] fruitArray = {"apple", "banana", "strawberry"};
        List<String> fruits = new ArrayList<String>(Arrays.asList(fruitArray));
        com.haru.mybatis.pattern.iterator.black.FruitAggregate fruitAggregate = new com.haru.mybatis.pattern.iterator
                .black.FruitAggregate(fruits);
        Iterator iterator = fruitAggregate.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void ChainTest() {
        StringHandler numberHandler = new NumberHandler();
        StringHandler letterHandler = new LetterHandler();
        StringHandler specialCharacterHandler = new SpecialCharacterHandler();
        numberHandler.setNextHandler(letterHandler).setNextHandler(specialCharacterHandler);
        numberHandler.handleString("a1sA@2d3$cA");
    }

    @Test
    public void commandTest() {
        ToyCar toyCar = new ToyCar();
        Command forwardCommand = new ForwardCommand(toyCar);
        Command backCommand = new BackCommand(toyCar);
        Controller controller = new Controller();
        controller.setCommand(forwardCommand);
        controller.executeCommand();
        controller.setCommand(backCommand);
        controller.executeCommand();
    }

    @Test
    public void MementoTest() {
        Origin origin = new Origin();
        origin.setValue("saveData1");
        Memento memento = origin.createMemento();
        MementoManagement mementoManagement = new MementoManagement();
        mementoManagement.setMemento(memento);
        System.out.println(origin.getValue());
        origin.setValue("saveData2");
        System.out.println(origin.getValue());
        origin.getMementoState(mementoManagement.getMemento());
        System.out.println(origin.getValue());
    }

    @Test
    public void StateTest() {
        Context context = new Context();
        context.setValue("alive");
        context.run();
        context.setValue("death");
        context.run();
    }

    @Test
    public void VisitorTest() {
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.addElement(new ElementA());
        objectStructure.addElement(new ElementB());
        objectStructure.accept(new VisitorA());
    }

    @Test
    public void MediatorTest() {
        ChatMediator chatMediator = new ChatMediator();
        ChatUserA chatUserA = new ChatUserA("熊猫人", chatMediator);
        ChatUserB chatUserB = new ChatUserB("蘑菇头", chatMediator);
        chatMediator.setChatUserA(chatUserA);
        chatMediator.setChatUserB(chatUserB);
        chatUserA.sendMessage("星期天上班");
        chatUserB.sendMessage("请假");
    }

    @Test
    public void interpreterTest() {
        com.haru.mybatis.pattern.interpreter.Context context = new com.haru.mybatis.pattern.interpreter.Context("number1 * number2 / number3 + number4 - number5");
        context.interpret();
    }
}
