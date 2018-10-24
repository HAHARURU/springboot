package com.haru.mybatis.pattern.state;

/**
 * @author HARU
 * @since 2018/10/23
 */
public class DeathState implements State {
    @Override
    public void action() {
        System.out.println("death action");
    }
}
