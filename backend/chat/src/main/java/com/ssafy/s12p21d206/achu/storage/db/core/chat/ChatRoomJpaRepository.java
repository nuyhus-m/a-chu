package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {

  boolean existsByGoodsIdAndSellerIdAndBuyerId(Long goodsId, Long sellerId, Long buyerId);

  @Query(
      "SELECT cr FROM ChatRoomEntity cr WHERE (cr.buyerId = :userId OR cr.sellerId = :userId) AND cr.entityStatus = :status")
  List<ChatRoomEntity> findByParticipant(Long userId, ChatEntityStatus status);

  @Query(
      "SELECT COUNT(cr) > 0 FROM ChatRoomEntity cr WHERE cr.id = :chatRoomId AND (cr.sellerId = :userId OR cr.buyerId = :userId)")
  boolean existsByIdAndUserId(Long chatRoomId, Long userId);

  @Modifying
  @Query(
      "UPDATE ChatRoomEntity cr SET cr.sellerLastReadMessageId = :lastReadMessageId WHERE cr.id = :chatRoomId AND cr.sellerId = :userId")
  void updateSellerLastReadMessageId(Long chatRoomId, Long userId, Long lastReadMessageId);

  @Modifying
  @Query(
      "UPDATE ChatRoomEntity cr SET cr.buyerLastReadMessageId = :lastReadMessageId WHERE cr.id = :chatRoomId AND cr.buyerId = :userId")
  void updateBuyerLastReadMessageId(Long chatRoomId, Long userId, Long lastReadMessageId);
}
