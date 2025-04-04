package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import java.time.LocalDateTime;

public record MessageResponse(
    Long id, String content, MessageType type, LocalDateTime timestamp, Long senderId) {
  public static MessageResponse from(Message message) {
    return new MessageResponse(
        message.id(),
        message.content(),
        message.type(),
        message.timestamp(),
        message.sender().id());
  }
}
