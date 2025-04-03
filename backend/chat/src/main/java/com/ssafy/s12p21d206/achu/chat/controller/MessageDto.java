package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import java.time.LocalDateTime;

public record MessageDto(
    Long id, Long senderId, MessageType type, String content, LocalDateTime timestamp) {

  public static MessageDto fromMessage(Message message) {
    return new MessageDto(
        message.id(),
        message.sender().id(),
        message.type(),
        message.content(),
        message.timestamp());
  }
}
