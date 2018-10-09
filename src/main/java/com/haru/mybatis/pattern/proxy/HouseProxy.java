package com.haru.mybatis.pattern.proxy;

/**
 * @author HARU
 * @since 2018/10/9
 */
public class HouseProxy implements House {

    private House house;

    public HouseProxy(House house) {
        this.house = house;
    }

    @Override
    public void buy() {
        System.out.println("start");    // 代理方法的关键处理
        house.buy();
        System.out.println("end");  // 代理方法的关键处理
    }
}
