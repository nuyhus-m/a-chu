package com.ssafy.s12p21d206.achu.api.controller.chat;

import com.ssafy.s12p21d206.achu.api.controller.goods.UserResponse;
import com.ssafy.s12p21d206.achu.domain.chat.ChatMessage;
import com.ssafy.s12p21d206.achu.domain.chat.ChatMessageWithSender;
import java.time.LocalDateTime;

public record ChatMessageDto(UserResponse sender, String message, LocalDateTime createdAt) {

  public static ChatMessageDto from(ChatMessageWithSender chatMessageWithSender) {

    ChatMessage chatMessage = chatMessageWithSender.chatMessage();
    UserResponse sender = UserResponse.from(chatMessageWithSender.sender());

    return new ChatMessageDto(
        sender, chatMessage.message(), chatMessage.defaultDateTime().createdAt());
  }

  public static ChatMessageDto from(ChatMessage chatMessage) {
    return new ChatMessageDto(
        null, chatMessage.message(), chatMessage.defaultDateTime().createdAt());
  }
}
