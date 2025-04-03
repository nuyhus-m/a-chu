package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {

  boolean existsByGoodsIdAndSellerIdAndBuyerId(Long goodsId, Long sellerId, Long buyerId);

  @Query(
      "SELECT cr FROM ChatRoomEntity cr WHERE (cr.buyerId = :id OR cr.sellerId = :id) AND cr.entityStatus = :status")
  List<ChatRoomEntity> findByParticipant(Long id, ChatEntityStatus status);
}
