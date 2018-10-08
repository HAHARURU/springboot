package com.haru.mybatis.pattern.adapter;

/**
 * @author HARU
 * @since 2018/10/8
 */
public class Voltage5VAdapter extends InterfaceVoltageAdapter {
    public Voltage5VAdapter(AC220 sourceVoltage) {
        super(sourceVoltage);
    }

    @Override
    public void charge5V() {
        sourceVoltage.charge();
        System.out.println("change DC5V");
    }
}
