package com.haru.mybatis.pattern.adapter;

/**
 * @author HARU
 * @since 2018/10/8
 */
public class ObjectVoltageAdapter implements DC5 {

    private AC220 sourceVoltage;

    public ObjectVoltageAdapter(AC220 sourceVoltage) {
        this.sourceVoltage = sourceVoltage;
    }

    @Override
    public void charge5V() {
        this.sourceVoltage.charge();
        System.out.println("change DC5V");
    }
}
