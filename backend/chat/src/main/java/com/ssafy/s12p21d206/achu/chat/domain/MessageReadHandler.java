package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.stereotype.Component;

@Component
public class MessageReadHandler {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatRoomValidator chatRoomValidator;

  public MessageReadHandler(
      ChatRoomRepository chatRoomRepository, ChatRoomValidator chatRoomValidator) {
    this.chatRoomRepository = chatRoomRepository;
    this.chatRoomValidator = chatRoomValidator;
  }

  public void updateRead(ChatUser user, Long roomId, Long lastReadMessageId) {
    chatRoomValidator.validateParticipant(roomId, user);
    chatRoomRepository.updateRead(user, roomId, lastReadMessageId);
  }
}
