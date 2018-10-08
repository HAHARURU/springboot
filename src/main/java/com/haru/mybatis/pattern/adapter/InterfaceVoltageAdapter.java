package com.haru.mybatis.pattern.adapter;

/**
 * @author HARU
 * @since 2018/10/8
 */
public abstract class InterfaceVoltageAdapter implements Voltage {

    protected AC220 sourceVoltage;

    public InterfaceVoltageAdapter(AC220 sourceVoltage) {
        this.sourceVoltage = sourceVoltage;
    }

    @Override
    public void charge5V() {
    }

    @Override
    public void charge9V() {
    }
}
