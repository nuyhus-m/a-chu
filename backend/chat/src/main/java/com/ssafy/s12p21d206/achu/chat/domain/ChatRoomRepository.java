package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
  ChatRoom save(ChatRoom chatRoom);

  Optional<ChatRoom> findById(Long roomId);

  List<ChatRoom> findActiveByUserId(ChatUser user);

  Optional<Long> findRoomIdByUsers(ChatUser user1, ChatUser user2);

  Optional<ChatUser> findPartnerIdByRoomIdAndUserId(Long roomId, ChatUser user);

  Optional<Long> findLastReadMessageIdByRoomIdAndUserId(Long roomId, ChatUser user);

  /**
   * 채팅방의 활성 상태를 확인
   */
  boolean isActiveForUser(Long roomId, ChatUser user);

  /**
   * 채팅방에 사용자의 참여 여부를 확인
   */
  boolean isUserInRoom(Long roomId, ChatUser user);

  ChatRoom leaveRoom(Long roomId, ChatUser user);

  ChatRoom updateLastReadMessageId(Long roomId, ChatUser user, Long messageId);
}
