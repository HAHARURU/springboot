package com.haru.mybatis.pattern.adapter;

/**
 * @author HARU
 * @since 2018/10/8
 */
public class VoltageAdapter extends AC220 implements DC5 {
    @Override
    public void charge5V() {
        charge();
        System.out.println("change DC5V");
    }
}
