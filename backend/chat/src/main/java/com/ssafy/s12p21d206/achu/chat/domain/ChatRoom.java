package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.time.LocalDateTime;

public record ChatRoom(
    Long id,
    ChatUser user1,
    ChatUser user2,
    boolean user1Active,
    boolean user2Active,
    Long user1LastReadMessageId,
    Long user2LastReadMessageId,
    LocalDateTime createdAt) {
  public static ChatRoom create(Long id, ChatUser user1, ChatUser user2, LocalDateTime createdAt) {
    return new ChatRoom(id, user1, user2, true, true, null, null, createdAt);
  }

  public ChatRoom withUser1Active(boolean active) {
    return new ChatRoom(
        id,
        user1,
        user2,
        active,
        user2Active,
        user1LastReadMessageId,
        user2LastReadMessageId,
        createdAt);
  }

  public ChatRoom withUser2Active(boolean active) {
    return new ChatRoom(
        id,
        user1,
        user2,
        user1Active,
        active,
        user1LastReadMessageId,
        user2LastReadMessageId,
        createdAt);
  }

  public ChatRoom withUser1LastReadMessageId(Long messageId) {
    return new ChatRoom(
        id, user1, user2, user1Active, user2Active, messageId, user2LastReadMessageId, createdAt);
  }

  public ChatRoom withUser2LastReadMessageId(Long messageId) {
    return new ChatRoom(
        id, user1, user2, user1Active, user2Active, user1LastReadMessageId, messageId, createdAt);
  }

  public ChatUser getPartnerOf(ChatUser user) {
    if (user.id().equals(user1.id())) {
      return user2;
    } else if (user.id().equals(user2.id())) {
      return user1;
    }
    throw new IllegalArgumentException("유저가 채팅방에 속해있지 않습니다.");
  }

  public boolean isParticipant(Long userId) {
    return user1.id().equals(userId) || user2.id().equals(userId);
  }

  public boolean isActiveFor(Long userId) {
    if (user1.id().equals(userId)) {
      return user1Active;
    } else if (user2.id().equals(userId)) {
      return user2Active;
    }
    throw new IllegalArgumentException("유저가 채팅방에 속해있지 않습니다.");
  }

  public Long getLastReadMessageIdFor(Long userId) {
    if (user1.id().equals(userId)) {
      return user1LastReadMessageId;
    } else if (user2.id().equals(userId)) {
      return user2LastReadMessageId;
    }
    throw new IllegalArgumentException("유저가 채팅방에 속해있지 않습니다.");
  }

  public boolean isPartnerActiveFor(Long userId) {
    if (user1.id().equals(userId)) {
      return user2Active;
    } else if (user2.id().equals(userId)) {
      return user1Active;
    }
    throw new IllegalArgumentException("유저가 채팅방에 속해있지 않습니다.");
  }

  public Long getPartnerIdOf(Long userId) {
    if (user1.id().equals(userId)) {
      return user2.id();
    } else if (user2.id().equals(userId)) {
      return user1.id();
    }
    throw new IllegalArgumentException("유저가 채팅방에 속해있지 않습니다.");
  }
}
