package com.haru.mybatis.pattern.decorator;

/**
 * @author HARU
 * @since 2018/10/7
 */
public abstract class CarDecorator implements Car {
    Car car;

    public CarDecorator(Car car) {
        this.car = car;
    }

    @Override
    public void make() {
        car.make();
    }
}
