package com.haru.mybatis.pattern.strategy;

/**
 * @author HARU
 * @since 2018/10/12
 */
public class AddMethod implements Calculator {
    @Override
    public void getResult() {
        System.out.println("add");
    }
}
