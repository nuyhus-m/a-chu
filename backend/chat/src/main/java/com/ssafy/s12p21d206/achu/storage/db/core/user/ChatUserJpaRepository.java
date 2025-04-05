package com.ssafy.s12p21d206.achu.storage.db.core.user;

import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatUserJpaRepository extends JpaRepository<ChatUserEntity, Long> {

  boolean existsByIdAndEntityStatus(Long id, ChatEntityStatus entityStatus);

  @Query(
      "SELECT s FROM ChatRoomEntity cr JOIN ChatUserEntity s ON cr.sellerId = s.id WHERE cr.id = :chatRoomId")
  Optional<ChatUserEntity> findSeller(Long chatRoomId);

  @Query(
      "SELECT b FROM ChatRoomEntity cr JOIN ChatUserEntity b ON cr.buyerId = b.id WHERE cr.id = :chatRoomId")
  Optional<ChatUserEntity> findBuyer(Long chatRoomId);
}
