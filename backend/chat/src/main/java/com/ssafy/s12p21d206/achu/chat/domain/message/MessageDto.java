package com.ssafy.s12p21d206.achu.chat.domain.message;

import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record MessageDto(
    Long id,
    String content,
    MessageType type,
    LocalDateTime timestamp,
    boolean isMine,
    Long senderId) {

  public static MessageDto from(Message message, ChatUser currentUser) {
    boolean isMine = message.sender().id().equals(currentUser.id());
    return new MessageDto(
        message.id(),
        message.content(),
        message.type(),
        message.timestamp(),
        isMine,
        message.sender().id());
  }

  public static List<MessageDto> listFrom(List<Message> messages, ChatUser currentUser) {
    return messages.stream()
        .map(message -> from(message, currentUser))
        .collect(Collectors.toList());
  }
}
