package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.time.LocalDateTime;
import java.util.List;

public record MessageResponse(
    Long id,
    String content,
    MessageType type,
    LocalDateTime timestamp,
    boolean isMine,
    Long senderId) {

  public static MessageResponse from(Message message, ChatUser currentUser) {
    boolean isMine = message.sender().id().equals(currentUser.id());
    return new MessageResponse(
        message.id(),
        message.content(),
        message.type(),
        message.timestamp(),
        isMine,
        message.sender().id());
  }

  public static List<MessageResponse> listFrom(List<Message> messages, ChatUser currentUser) {
    return messages.stream().map(message -> from(message, currentUser)).toList();
  }
}
