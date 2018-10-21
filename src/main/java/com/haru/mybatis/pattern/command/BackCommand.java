package com.haru.mybatis.pattern.command;

/**
 * @author HARU
 * @since 2018/10/21
 */
public class BackCommand implements Command {
    private ToyCar toyCar;

    public BackCommand(ToyCar toyCar) {
        this.toyCar = toyCar;
    }

    @Override
    public void execute() {
        toyCar.Back();
    }
}
