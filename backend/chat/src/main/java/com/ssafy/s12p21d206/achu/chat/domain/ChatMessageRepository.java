package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository {
  ChatMessage save(NewChatMessage message);

  Optional<ChatMessage> findById(Long messageId);

  /**
   * 특정 메시지 ID 이전의 메시지를 조회합니다.
   * before가 null이면 최신 메시지부터 조회합니다.
   *
   * @param roomId 채팅방 ID
   * @param before 이 메시지 ID 이전의 메시지를 조회 (null일 경우 최신 메시지부터)
   * @param limit  최대 조회 개수
   * @return 메시지 목록
   */
  List<ChatMessage> findByRoomIdBeforeId(Long roomId, Long before, Integer limit);

  List<ChatMessage> findLatestByRoomIds(List<Long> roomIds);

  long countUnreadMessages(Long roomId, ChatUser user, Long lastReadMessageId);

  boolean existsById(Long messageId);
}
