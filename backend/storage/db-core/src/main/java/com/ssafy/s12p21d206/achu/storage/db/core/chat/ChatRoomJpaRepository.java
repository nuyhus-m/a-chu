package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {
  @Query("SELECT c FROM ChatRoomEntity c where c.sellerId = :userId or c.buyerId = :userId")
  List<ChatRoomEntity> findByUserId(Long userId);
}
