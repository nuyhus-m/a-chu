package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomFacade {

  private final ChatRoomService chatRoomService;
  private final MessageService messageService;
  private final ChatFcmEventPublisher chatFcmEventPublisher;

  public ChatRoomFacade(
      ChatRoomService chatRoomService,
      MessageService messageService,
      ChatFcmEventPublisher chatFcmEventPublisher) {
    this.chatRoomService = chatRoomService;
    this.messageService = messageService;
    this.chatFcmEventPublisher = chatFcmEventPublisher;
  }

  @Transactional
  public Long append(ChatUser buyer, NewChatRoom newChatRoom, NewMessage newMessage) {
    ChatRoom chatRoom = chatRoomService.append(buyer, newChatRoom);
    Message message = messageService.append(buyer, chatRoom, newMessage);
    chatFcmEventPublisher.publishChatRoomCreateEvent(chatRoom);
    return chatRoom.updateLastMessage(message).id();
  }
}
