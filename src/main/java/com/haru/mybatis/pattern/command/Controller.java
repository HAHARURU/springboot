package com.haru.mybatis.pattern.command;

/**
 * @author HARU
 * @since 2018/10/21
 */
public class Controller {
    private Command forwardCommand;
    private Command backCommand;

    public void setForwardCommand(Command forwardCommand) {
        this.forwardCommand = forwardCommand;
    }

    public void setBackCommand(Command backCommand) {
        this.backCommand = backCommand;
    }

    public void forward() {
        forwardCommand.execute();
    }

    public void back() {
        backCommand.execute();
    }
}
