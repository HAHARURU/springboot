package com.haru.mybatis.pattern.mediator;

/**
 * @author HARU
 * @since 2018/10/25
 */
public class ChatMediator implements Mediator {

    private ChatUserA chatUserA;
    private ChatUserB chatUserB;

    public void setChatUserA(ChatUserA chatUserA) {
        this.chatUserA = chatUserA;
    }

    public void setChatUserB(ChatUserB chatUserB) {
        this.chatUserB = chatUserB;
    }

    @Override
    public void broadcast(ChatUser chatUser, String message) {
        if (chatUser == chatUserA) {
            chatUserB.receive(message);
        }
        if (chatUser == chatUserB) {
            chatUserA.refuse(message);
        }
    }
}
