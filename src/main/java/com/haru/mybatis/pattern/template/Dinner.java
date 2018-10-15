package com.haru.mybatis.pattern.template;

/**
 * @author HARU
 * @since 2018/10/15
 */
public abstract class Dinner {

    void wash() {
        System.out.println("wash");
    }

    abstract void cut();

    abstract void cook();

    void sabot() {
        System.out.println("sabot");
    }

    public final void mark() {
        wash();
        cut();
        cook();
        sabot();
    }
}
