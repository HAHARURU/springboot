package com.haru.mybatis.pattern.state;

/**
 * @author HARU
 * @since 2018/10/23
 */
public class AliveState implements State {
    @Override
    public void action() {
        System.out.println("alive action");
    }
}
