package com.ssafy.s12p21d206.achu.chat.storage.db.core;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.storage.db.core.support.ChatBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
public class ChatRoomEntity extends ChatBaseEntity {

  private Long user1Id;
  private Long user2Id;
  private boolean user1Active;
  private boolean user2Active;
  private Long user1LastReadMessageId;
  private Long user2LastReadMessageId;
  private LocalDateTime createdAt;

  protected ChatRoomEntity() {}

  public ChatRoomEntity(Long user1Id, Long user2Id, LocalDateTime createdAt) {
    this.user1Id = user1Id;
    this.user2Id = user2Id;
    this.user1Active = true;
    this.user2Active = true;
    this.createdAt = createdAt;
  }

  public ChatRoomEntity(
      Long id,
      Long user1Id,
      Long user2Id,
      boolean user1Active,
      boolean user2Active,
      Long user1LastReadMessageId,
      Long user2LastReadMessageId,
      LocalDateTime createdAt) {
    super(id);
    this.user1Id = user1Id;
    this.user2Id = user2Id;
    this.user1Active = user1Active;
    this.user2Active = user2Active;
    this.user1LastReadMessageId = user1LastReadMessageId;
    this.user2LastReadMessageId = user2LastReadMessageId;
    this.createdAt = createdAt;
  }

  public ChatRoom toDomain() {
    return new ChatRoom(
        getId(),
        new ChatUser(user1Id),
        new ChatUser(user2Id),
        user1Active,
        user2Active,
        user1LastReadMessageId,
        user2LastReadMessageId,
        createdAt);
  }

  public static ChatRoomEntity fromDomain(ChatRoom chatRoom) {
    ChatRoomEntity entity;

    if (chatRoom.id() != null) {
      entity = new ChatRoomEntity(
          chatRoom.id(),
          chatRoom.user1().id(),
          chatRoom.user2().id(),
          chatRoom.user1Active(),
          chatRoom.user2Active(),
          chatRoom.user1LastReadMessageId(),
          chatRoom.user2LastReadMessageId(),
          chatRoom.createdAt());
    } else {
      entity =
          new ChatRoomEntity(chatRoom.user1().id(), chatRoom.user2().id(), chatRoom.createdAt());
      entity.user1Active = chatRoom.user1Active();
      entity.user2Active = chatRoom.user2Active();
      entity.user1LastReadMessageId = chatRoom.user1LastReadMessageId();
      entity.user2LastReadMessageId = chatRoom.user2LastReadMessageId();
    }

    return entity;
  }

  public Long getUser1Id() {
    return user1Id;
  }

  public Long getUser2Id() {
    return user2Id;
  }

  public boolean isUser1Active() {
    return user1Active;
  }

  public boolean isUser2Active() {
    return user2Active;
  }

  public Long getUser1LastReadMessageId() {
    return user1LastReadMessageId;
  }

  public Long getUser2LastReadMessageId() {
    return user2LastReadMessageId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setUser1Active(boolean active) {
    this.user1Active = active;
  }

  public void setUser2Active(boolean active) {
    this.user2Active = active;
  }

  public void setUser1LastReadMessageId(Long messageId) {
    this.user1LastReadMessageId = messageId;
  }

  public void setUser2LastReadMessageId(Long messageId) {
    this.user2LastReadMessageId = messageId;
  }

  /**
   * 채팅방이 활성 상태인지 확인
   * 채팅방은 적어도 한 명의 사용자가 활성 상태일 때 활성 상태로 간주
   */
  @Override
  public boolean isActive() {
    return user1Active || user2Active;
  }

  /**
   * 채팅방 삭제
   * 두 사용자 모두 비활성 상태로 설정하고 삭제 상태로 변경
   */
  @Override
  public void delete() {
    this.user1Active = false;
    this.user2Active = false;
    super.delete();
  }
}
