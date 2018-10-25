package com.haru.mybatis.pattern.mediator;

/**
 * @author HARU
 * @since 2018/10/25
 */
public interface Mediator {
    void broadcast(ChatUser chatUser, String message);
}
