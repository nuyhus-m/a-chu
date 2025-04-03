package com.ssafy.s12p21d206.achu.chat.storage.db.core;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {
  /**
   * 사용자 ID로 활성 상태인 모든 채팅방을 조회합니다.
   *
   * @param userId 사용자 ID
   * @return 활성 상태인 채팅방 목록
   */
  @Query("SELECT r FROM ChatRoomEntity r " + "WHERE (r.user1Id = :userId AND r.user1Active = true) "
      + "OR (r.user2Id = :userId AND r.user2Active = true)")
  List<ChatRoomEntity> findActiveByUserId(@Param("userId") Long userId);

  /**
   * 두 사용자 ID로 채팅방 존재 여부를 확인합니다.
   *
   * @param userId1 첫 번째 사용자 ID
   * @param userId2 두 번째 사용자 ID
   * @return 채팅방 존재 여부
   */
  @Query("SELECT COUNT(r) > 0 FROM ChatRoomEntity r "
      + "WHERE (r.user1Id = :userId1 AND r.user2Id = :userId2) "
      + "OR (r.user1Id = :userId2 AND r.user2Id = :userId1)")
  boolean existsByUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

  /**
   * 두 사용자 ID로 채팅방 ID를 조회합니다.
   *
   * @param userId1 첫 번째 사용자 ID
   * @param userId2 두 번째 사용자 ID
   * @return 채팅방 ID (Optional)
   */
  @Query("SELECT r.id FROM ChatRoomEntity r "
      + "WHERE (r.user1Id = :userId1 AND r.user2Id = :userId2) "
      + "OR (r.user1Id = :userId2 AND r.user2Id = :userId1)")
  Optional<Long> findRoomIdByUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

  /**
   * 채팅방 ID와 사용자 ID로 상대방 ID를 조회합니다.
   *
   * @param roomId 채팅방 ID
   * @param userId 사용자 ID
   * @return 상대방 ID (Optional)
   */
  @Query("SELECT CASE " + "WHEN r.user1Id = :userId THEN r.user2Id "
      + "WHEN r.user2Id = :userId THEN r.user1Id "
      + "ELSE NULL END "
      + "FROM ChatRoomEntity r "
      + "WHERE r.id = :roomId")
  Optional<Long> findPartnerIdByRoomIdAndUserId(
      @Param("roomId") Long roomId, @Param("userId") Long userId);

  /**
   * 채팅방 ID와 사용자 ID로 마지막으로 읽은 메시지 ID를 조회합니다.
   *
   * @param roomId 채팅방 ID
   * @param userId 사용자 ID
   * @return 마지막으로 읽은 메시지 ID (Optional)
   */
  @Query("SELECT CASE " + "WHEN r.user1Id = :userId THEN r.user1LastReadMessageId "
      + "WHEN r.user2Id = :userId THEN r.user2LastReadMessageId "
      + "ELSE NULL END "
      + "FROM ChatRoomEntity r "
      + "WHERE r.id = :roomId")
  Optional<Long> findLastReadMessageIdByRoomIdAndUserId(
      @Param("roomId") Long roomId, @Param("userId") Long userId);
}
