package com.ssafy.s12p21d206.achu.chat.storage.db.core;

import com.ssafy.s12p21d206.achu.chat.domain.ChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.NewChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.storage.db.core.support.ChatBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_message")
public class ChatMessageEntity extends ChatBaseEntity {

  private Long roomId;

  private Long senderId;

  private String content;

  @Enumerated(EnumType.STRING)
  private MessageType type;

  protected ChatMessageEntity() {}

  public ChatMessageEntity(Long roomId, Long senderId, String content, MessageType type) {
    this.roomId = roomId;
    this.senderId = senderId;
    this.content = content;
    this.type = type;
  }

  public ChatMessage toChatMessage() {
    return new ChatMessage(
        getId(), roomId, new ChatUser(senderId), content, type, this.getCreatedAt());
  }

  public static ChatMessageEntity fromNewChatMessage(NewChatMessage message) {
    return new ChatMessageEntity(
        message.roomId(), message.sender().id(), message.content(), message.type());
  }

  public Long getRoomId() {
    return roomId;
  }

  public Long getSenderId() {
    return senderId;
  }

  public String getContent() {
    return content;
  }

  public MessageType getType() {
    return type;
  }
}
