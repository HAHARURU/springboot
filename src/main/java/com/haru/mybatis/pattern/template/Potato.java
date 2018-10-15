package com.haru.mybatis.pattern.template;

/**
 * @author HARU
 * @since 2018/10/15
 */
public class Potato extends Dinner {
    @Override
    void cut() {
        System.out.println("cut block");
    }

    @Override
    void cook() {
        System.out.println("stew");
    }
}
