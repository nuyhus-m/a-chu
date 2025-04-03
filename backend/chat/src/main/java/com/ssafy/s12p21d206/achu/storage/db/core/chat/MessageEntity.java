package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.NewMessage;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Table(
    name = "chat_message",
    indexes = {@Index(name = "idx_chatroom_created", columnList = "chatRoomId,createdAt")})
@Entity
public class MessageEntity extends ChatBaseEntity {

  private Long chatRoomId;
  private String content;
  private Long senderId;

  @Enumerated(EnumType.STRING)
  private MessageType type;

  protected MessageEntity() {}

  public MessageEntity(Long chatRoomId, String content, Long senderId, MessageType type) {
    this.chatRoomId = chatRoomId;
    this.content = content;
    this.senderId = senderId;
    this.type = type;
  }

  public static MessageEntity fromNewMessage(
      ChatUser sender, ChatRoom chatRoom, NewMessage newMessage) {
    return new MessageEntity(chatRoom.id(), newMessage.content(), sender.id(), newMessage.type());
  }

  public Message toMessage() {
    return new Message(getId(), chatRoomId, content, new ChatUser(senderId), type, getCreatedAt());
  }
}
