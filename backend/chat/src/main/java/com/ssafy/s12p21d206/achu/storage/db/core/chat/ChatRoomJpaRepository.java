package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {

  boolean existsByGoodsIdAndSellerIdAndBuyerId(Long goodsId, Long sellerId, Long buyerId);

  @Query(
      "SELECT COUNT(cr) > 0 FROM ChatRoomEntity cr WHERE cr.id = :chatRoomId AND (cr.sellerId = :userId OR cr.buyerId = :userId)")
  boolean existsByIdAndUserId(Long chatRoomId, Long userId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE ChatRoomEntity cr SET cr.sellerLastReadMessageId = :lastReadMessageId WHERE cr.id = :chatRoomId AND cr.sellerId = :userId")
  void updateSellerLastReadMessageId(Long chatRoomId, Long userId, Long lastReadMessageId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE ChatRoomEntity cr SET cr.buyerLastReadMessageId = :lastReadMessageId WHERE cr.id = :chatRoomId AND cr.buyerId = :userId")
  void updateBuyerLastReadMessageId(Long chatRoomId, Long userId, Long lastReadMessageId);

  @Query(
      """
            SELECT COUNT(m) FROM MessageEntity m
            JOIN ChatRoomEntity cr ON m.chatRoomId = cr.id
            WHERE m.entityStatus = 'ACTIVE'
            AND ((cr.sellerId = :userId AND m.id > COALESCE(cr.sellerLastReadMessageId, 0))
                OR (cr.buyerId = :userId AND m.id > COALESCE(cr.buyerLastReadMessageId, 0)))
            AND (cr.sellerId = :userId OR cr.buyerId = :userId)
            """)
  Long countUnreadMessagesByUserId(@Param("userId") Long userId);

  @Query(
      """
            SELECT COUNT(m) FROM MessageEntity m
            JOIN ChatRoomEntity cr ON m.chatRoomId = cr.id
            WHERE m.entityStatus = 'ACTIVE'
            AND ((cr.sellerId = :userId AND m.id > COALESCE(cr.sellerLastReadMessageId, 0))
                OR (cr.buyerId = :userId AND m.id > COALESCE(cr.buyerLastReadMessageId, 0)))
            AND (cr.sellerId = :userId OR cr.buyerId = :userId)
            AND cr.id = :chatRoomId
            """)
  Long countUnreadMessagesByUserIdAndChatRoomId(
      @Param("userId") Long userId, @Param("chatRoomId") Long chatRoomId);

  @Query(
      """
            SELECT cr.id as chatRoomId, COUNT(m) as unreadCount
            FROM MessageEntity m
            JOIN ChatRoomEntity cr ON m.chatRoomId = cr.id
            WHERE m.entityStatus = 'ACTIVE'
            AND ((cr.sellerId = :userId AND m.id > COALESCE(cr.sellerLastReadMessageId, 0))
                OR (cr.buyerId = :userId AND m.id > COALESCE(cr.buyerLastReadMessageId, 0)))
            AND (cr.sellerId = :userId OR cr.buyerId = :userId)
            AND cr.id IN :chatRoomIds
            GROUP BY cr.id
            """)
  List<Object[]> countUnreadMessagesByUserIdAndChatRoomIds(
      @Param("userId") Long userId, @Param("chatRoomIds") List<Long> chatRoomIds);

  @Query(
      """
            SELECT new com.ssafy.s12p21d206.achu.storage.db.core.chat.ChatRoomDto(
                cr.id, cr.goodsId, cr.sellerId, cr.buyerId,
                cr.sellerLastReadMessageId, cr.buyerLastReadMessageId, cr.isSellerLeft, cr.isBuyerLeft,
                g.title, g.thumbnailImageUrl,
                seller.nickname, seller.profileImageUrl,
                buyer.nickname, buyer.profileImageUrl,
                m.id, m.content, m.senderId, m.type, m.createdAt)
            FROM ChatRoomEntity cr
            JOIN ChatUserEntity seller ON cr.sellerId = seller.id
            JOIN ChatUserEntity buyer ON cr.buyerId = buyer.id
            JOIN ChatGoodsEntity g ON cr.goodsId = g.id
            LEFT JOIN MessageEntity m ON m.chatRoomId = cr.id AND m.createdAt = (
                SELECT MAX(m2.createdAt)
                FROM MessageEntity m2
                WHERE m2.chatRoomId = cr.id
                AND m2.entityStatus = 'ACTIVE'
            )
            WHERE ((cr.buyerId = :userId AND NOT cr.isBuyerLeft) OR (cr.sellerId = :userId AND NOT cr.isBuyerLeft))
            AND cr.entityStatus = :status
            AND seller.entityStatus = 'ACTIVE'
            AND buyer.entityStatus = 'ACTIVE'
            AND g.entityStatus = 'ACTIVE'
            ORDER BY m.createdAt DESC
            """)
  List<ChatRoomDto> findByParticipant(
      @Param("userId") Long userId, @Param("status") ChatEntityStatus status);

  @Query(
      """
            SELECT new com.ssafy.s12p21d206.achu.storage.db.core.chat.ChatRoomDto(
                cr.id, cr.goodsId, cr.sellerId, cr.buyerId,
                cr.sellerLastReadMessageId, cr.buyerLastReadMessageId, cr.isSellerLeft, cr.isBuyerLeft,
                g.title, g.thumbnailImageUrl,
                seller.nickname, seller.profileImageUrl,
                buyer.nickname, buyer.profileImageUrl,
                m.id, m.content, m.senderId, m.type, m.createdAt)
            FROM ChatRoomEntity cr
            JOIN ChatUserEntity seller ON cr.sellerId = seller.id
            JOIN ChatUserEntity buyer ON cr.buyerId = buyer.id
            JOIN ChatGoodsEntity g ON cr.goodsId = g.id
            LEFT JOIN MessageEntity m ON m.chatRoomId = cr.id AND m.createdAt = (
                SELECT MAX(m2.createdAt)
                FROM MessageEntity m2
                WHERE m2.chatRoomId = cr.id
                AND m2.entityStatus = 'ACTIVE'
            )
            WHERE cr.id = :chatRoomId
            AND cr.entityStatus = :status
            AND seller.entityStatus = 'ACTIVE'
            AND buyer.entityStatus = 'ACTIVE'
            AND g.entityStatus = 'ACTIVE'
            """)
  Optional<ChatRoomDto> findById(
      @Param("chatRoomId") Long chatRoomId, @Param("status") ChatEntityStatus status);

  @Query(
      """
  select cr.id from ChatRoomEntity cr
  where cr.goodsId = :goodsId and cr.sellerId = :sellerId and cr.buyerId = :buyerId
  and cr.entityStatus = 'ACTIVE'
  """)
  Optional<Long> findByGoodsIdAndSellerIdAndBuyerId(Long goodsId, Long id, Long id1);
}
