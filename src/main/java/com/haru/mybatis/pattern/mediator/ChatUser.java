package com.haru.mybatis.pattern.mediator;

/**
 * @author HARU
 * @since 2018/10/25
 */
public abstract class ChatUser {
    String name;
    Mediator mediator;

    public ChatUser(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public void sendMessage(String message) {
        System.out.println(name + "发出“" + message + "”的信息");
        mediator.broadcast(this, message);
    }
}
