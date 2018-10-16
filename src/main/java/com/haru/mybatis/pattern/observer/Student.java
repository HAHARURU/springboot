package com.haru.mybatis.pattern.observer;

/**
 * @author HARU
 * @since 2018/10/16
 */
public class Student implements User {
    @Override
    public void update(String message) {
        System.out.println("student get " + message);
    }
}
