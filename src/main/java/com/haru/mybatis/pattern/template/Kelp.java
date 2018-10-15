package com.haru.mybatis.pattern.template;

/**
 * @author HARU
 * @since 2018/10/15
 */
public class Kelp extends Dinner {
    @Override
    void cut() {
        System.out.println("cut silk");
    }

    @Override
    void cook() {
        System.out.println("fry");
    }
}
