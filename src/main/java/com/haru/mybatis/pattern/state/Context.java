package com.haru.mybatis.pattern.state;

/**
 * @author HARU
 * @since 2018/10/23
 */
public class Context {
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
