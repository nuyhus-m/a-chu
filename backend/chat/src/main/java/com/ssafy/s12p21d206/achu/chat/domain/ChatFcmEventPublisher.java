package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.ChatRoomCreateEvent;
import com.ssafy.s12p21d206.achu.NewChatMessageEvent;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ChatFcmEventPublisher {
  private final ApplicationEventPublisher eventPublisher;

  public ChatFcmEventPublisher(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public void publishChatRoomCreateEvent(ChatRoom chatRoom) {
    eventPublisher.publishEvent(new ChatRoomCreateEvent(
        this, chatRoom.seller().id(), chatRoom.id(), chatRoom.goods().title()));
  }

  public void publishNewChatMessageEvent(
      ChatUser sender, ChatUser partner, ChatRoom chatRoom, Message message) {
    String senderNickname = chatRoom.seller().id().equals(sender.id())
        ? chatRoom.seller().nickname()
        : chatRoom.buyer().nickname();
    eventPublisher.publishEvent(new NewChatMessageEvent(
        this, partner.id(), senderNickname, message.chatRoomId(), message.content()));
  }
}
