package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {
  List<ChatMessageEntity> findByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);
}
