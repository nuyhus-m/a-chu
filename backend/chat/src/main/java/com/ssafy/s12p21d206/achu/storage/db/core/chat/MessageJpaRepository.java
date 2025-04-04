package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {

  @Query(
      """
            SELECT m FROM MessageEntity m
            WHERE m.entityStatus = 'ACTIVE'
            AND m.chatRoomId IN :chatRoomIds
            AND (m.chatRoomId, m.createdAt) IN (
                SELECT m2.chatRoomId, MAX(m2.createdAt)
                FROM MessageEntity m2
                WHERE m2.chatRoomId IN :chatRoomIds
                AND m2.entityStatus = 'ACTIVE'
                GROUP BY m2.chatRoomId
            )
            """)
  List<MessageEntity> findLastMessagesInChatRoomIds(@Param("chatRoomIds") List<Long> chatRoomIds);

  @Query(
      """
            SELECT m FROM MessageEntity m
            WHERE m.entityStatus = 'ACTIVE'
            AND m.chatRoomId = :chatRoomId
            ORDER BY m.createdAt ASC
            """)
  List<MessageEntity> findAllByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
