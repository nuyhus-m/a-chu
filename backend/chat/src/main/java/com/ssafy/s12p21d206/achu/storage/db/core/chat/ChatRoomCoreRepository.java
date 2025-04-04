package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomRepository;
import com.ssafy.s12p21d206.achu.chat.domain.NewChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomCoreRepository implements ChatRoomRepository {

  private final ChatRoomJpaRepository chatRoomJpaRepository;

  public ChatRoomCoreRepository(ChatRoomJpaRepository chatRoomJpaRepository) {
    this.chatRoomJpaRepository = chatRoomJpaRepository;
  }

  @Override
  public boolean existsBy(Long goodsId, ChatUser seller, ChatUser buyer) {
    return chatRoomJpaRepository.existsByGoodsIdAndSellerIdAndBuyerId(
        goodsId, seller.id(), buyer.id());
  }

  @Override
  public Long save(ChatUser buyer, NewChatRoom newChatRoom) {
    ChatRoomEntity chatRoomEntity = ChatRoomEntity.fromNewChatRoom(buyer, newChatRoom);
    return chatRoomJpaRepository.save(chatRoomEntity).getId();
  }

  @Override
  public List<ChatRoom> readChatRooms(ChatUser viewer) {
    return chatRoomJpaRepository.findByParticipant(viewer.id(), ChatEntityStatus.ACTIVE).stream()
        .map(ChatRoomDto::toChatRoom)
        .toList();
  }

  @Override
  public Optional<ChatRoom> findById(Long chatRoomId) {
    return chatRoomJpaRepository
        .findById(chatRoomId, ChatEntityStatus.ACTIVE)
        .map(ChatRoomDto::toChatRoom);
  }

  @Override
  public boolean isParticipant(Long chatRoomId, ChatUser user) {
    return chatRoomJpaRepository.existsByIdAndUserId(chatRoomId, user.id());
  }

  @Override
  public void updateRead(ChatUser user, Long roomId, Long lastReadMessageId) {
    Optional<ChatRoomEntity> chatRoomOptional = chatRoomJpaRepository.findById(roomId);
    if (chatRoomOptional.isEmpty()) {
      return;
    }

    ChatRoomEntity chatRoom = chatRoomOptional.get();
    if (chatRoom.getSellerId().equals(user.id())) {
      chatRoomJpaRepository.updateSellerLastReadMessageId(roomId, user.id(), lastReadMessageId);
    } else if (chatRoom.getBuyerId().equals(user.id())) {
      chatRoomJpaRepository.updateBuyerLastReadMessageId(roomId, user.id(), lastReadMessageId);
    }
  }

  @Override
  public UnreadCount countUnreadMessages(ChatUser user) {
    return new UnreadCount(chatRoomJpaRepository.countUnreadMessagesByUserId(user.id()));
  }

  @Override
  public UnreadCount countUnreadMessages(ChatUser user, Long chatRoomId) {
    return new UnreadCount(
        chatRoomJpaRepository.countUnreadMessagesByUserIdAndChatRoomId(user.id(), chatRoomId));
  }

  @Override
  public Map<Long, UnreadCount> countUnreadMessages(ChatUser user, List<Long> chatRoomIds) {
    if (chatRoomIds == null || chatRoomIds.isEmpty()) {
      return Map.of();
    }

    List<Object[]> results =
        chatRoomJpaRepository.countUnreadMessagesByUserIdAndChatRoomIds(user.id(), chatRoomIds);
    Map<Long, UnreadCount> unreadCountMap = new HashMap<>();

    for (Object[] result : results) {
      Long chatRoomId = (Long) result[0];
      Long count = ((Number) result[1]).longValue();
      unreadCountMap.put(chatRoomId, new UnreadCount(count));
    }

    // 결과에 없는 채팅방은 0으로 설정
    for (Long chatRoomId : chatRoomIds) {
      unreadCountMap.putIfAbsent(chatRoomId, new UnreadCount(0L));
    }

    return unreadCountMap;
  }
}
