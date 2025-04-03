package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.time.LocalDateTime;

public record ChatMessage(
    Long id,
    Long roomId,
    ChatUser sender,
    String content,
    MessageType type,
    LocalDateTime timestamp) {

  public static ChatMessage from(Long id, NewChatMessage newMessage) {
    return new ChatMessage(
        id,
        newMessage.roomId(),
        newMessage.sender(),
        newMessage.content(),
        newMessage.type(),
        newMessage.timestamp());
  }
}
