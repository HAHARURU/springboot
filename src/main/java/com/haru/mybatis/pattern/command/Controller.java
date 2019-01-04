package com.haru.mybatis.pattern.command;

/**
 * @author HARU
 * @since 2018/10/21
 */
public class Controller {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}
