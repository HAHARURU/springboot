package com.haru.mybatis.pattern.decorator;

/**
 * @author HARU
 * @since 2018/10/7
 */
public class Benz implements Car {
    @Override
    public void make() {
        System.out.println("make Benz");
    }
}
