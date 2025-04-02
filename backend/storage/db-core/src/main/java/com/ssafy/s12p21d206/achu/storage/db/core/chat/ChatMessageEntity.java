package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.domain.chat.ChatMessage;
import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;
import com.ssafy.s12p21d206.achu.storage.db.core.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "chat_message")
public class ChatMessageEntity extends BaseEntity {

  private Long chatRoomId;

  private Long senderId;

  private String message;

  protected ChatMessageEntity() {}

  public ChatMessageEntity(Long chatRoomId, Long senderId, String message) {
    this.chatRoomId = chatRoomId;
    this.senderId = senderId;
    this.message = message;
  }

  public ChatMessage toChatMessage() {
    return new ChatMessage(
        getId(),
        this.chatRoomId,
        this.senderId,
        this.message,
        new DefaultDateTime(getCreatedAt(), getUpdatedAt()));
  }
}
