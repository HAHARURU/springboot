package com.haru.mybatis.pattern.mediator;

/**
 * @author HARU
 * @since 2018/10/25
 */
public class ChatUserA extends ChatUser {

    public ChatUserA(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void refuse(String message) {
        System.out.println(name + "不理会“" + message + "”的消息");
    }
}
