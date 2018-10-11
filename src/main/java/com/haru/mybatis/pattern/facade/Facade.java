package com.haru.mybatis.pattern.facade;

/**
 * @author HARU
 * @since 2018/10/11
 */
public class Facade {
    private SubSystem1 subSystem1;
    private SubSystem2 subSystem2;

    public Facade() {
        subSystem1 = new SubSystem1();
        subSystem2 = new SubSystem2();
    }

    public void run() {
        System.out.println("start");
        subSystem1.operation1();
        subSystem2.operation3();
        System.out.println("end");
    }
}
