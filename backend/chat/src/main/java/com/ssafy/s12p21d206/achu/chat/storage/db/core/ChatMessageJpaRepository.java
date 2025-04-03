package com.ssafy.s12p21d206.achu.chat.storage.db.core;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {

  List<ChatMessageEntity> findByRoomIdOrderByTimestampDesc(Long roomId, Pageable pageable);

  @Query("SELECT m FROM ChatMessageEntity m "
      + "WHERE m.roomId = :roomId AND m.id < :lastMessageId " + "ORDER BY m.timestamp DESC")
  List<ChatMessageEntity> findByRoomIdWithPaging(
      @Param("roomId") Long roomId, @Param("lastMessageId") Long lastMessageId, Pageable pageable);

  @Query("SELECT m FROM ChatMessageEntity m "
      + "WHERE m.roomId = :roomId "
      + "AND (:before IS NULL OR m.id < :before) "
      + "ORDER BY m.timestamp DESC")
  List<ChatMessageEntity> findByRoomIdBeforeId(
      @Param("roomId") Long roomId, @Param("before") Long before, Pageable pageable);

  @Query(
      "SELECT m FROM ChatMessageEntity m " + "WHERE m.roomId IN :roomIds "
          + "AND m.id IN (SELECT MAX(m2.id) FROM ChatMessageEntity m2 WHERE m2.roomId IN :roomIds GROUP BY m2.roomId)")
  List<ChatMessageEntity> findLatestByRoomIds(@Param("roomIds") List<Long> roomIds);

  @Query("SELECT COUNT(m) FROM ChatMessageEntity m "
      + "WHERE m.roomId = :roomId AND m.id > :lastReadMessageId")
  long countUnreadMessages(
      @Param("roomId") Long roomId, @Param("lastReadMessageId") Long lastReadMessageId);

  @Query("SELECT m.id FROM ChatMessageEntity m "
      + "WHERE m.roomId = :roomId AND m.id > :lastReadMessageId")
  List<Long> findUnreadMessageIds(
      @Param("roomId") Long roomId, @Param("lastReadMessageId") Long lastReadMessageId);
}
