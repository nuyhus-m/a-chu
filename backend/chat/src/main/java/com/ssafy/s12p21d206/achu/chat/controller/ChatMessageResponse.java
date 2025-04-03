package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.ChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import java.time.LocalDateTime;

public record ChatMessageResponse(
    Long messageId,
    Long roomId,
    Long senderId,
    String content,
    LocalDateTime timestamp,
    MessageType type,
    boolean read) {
  public static ChatMessageResponse from(ChatMessage message, boolean read) {
    return new ChatMessageResponse(
        message.id(),
        message.roomId(),
        message.sender().id(),
        message.content(),
        message.timestamp(),
        message.type(),
        read);
  }
}
