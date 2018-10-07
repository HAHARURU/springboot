package com.haru.mybatis.pattern.decorator;

/**
 * @author HARU
 * @since 2018/10/7
 */
public class TailCarDecorator extends CarDecorator {
    public TailCarDecorator(Car car) {
        super(car);
    }

    @Override
    public void make() {
        car.make();
        this.setTail(car);
    }

    // 装饰的方法
    private void setTail(Car car) {
        System.out.println("set tail");
    }
}
