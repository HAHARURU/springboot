package com.haru.mybatis.pattern.strategy;

/**
 * @author HARU
 * @since 2018/10/12
 */
public class MathContext {
    private Calculator calculator;

    public MathContext(Calculator calculator) {
        this.calculator = calculator;
    }

    public void run() {
        calculator.getResult();
    }
}
