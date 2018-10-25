package com.haru.mybatis.pattern.mediator;

/**
 * @author HARU
 * @since 2018/10/25
 */
public class ChatUserB extends ChatUser {

    public ChatUserB(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void receive(String message) {
        System.out.println(name + "收到“" + message + "”的信息");
    }
}
