package com.haru.mybatis.pattern.state;

/**
 * @author HARU
 * @since 2018/10/23
 */
public class Context {
    private State state;

    private String value;

    public void run() {
        changeState();
        this.state.action();
    }

    private void changeState() {
        if ("alive".equals(value)) {
            this.state = new AliveState();
        }
        if ("death".equals(value)) {
            this.state = new DeathState();
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
