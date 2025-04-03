package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomRepository;
import com.ssafy.s12p21d206.achu.chat.domain.NewChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.List;
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
  public ChatRoom save(ChatUser buyer, NewChatRoom newChatRoom) {
    ChatRoomEntity chatRoomEntity = ChatRoomEntity.fromNewChatRoom(buyer, newChatRoom);
    return chatRoomJpaRepository.save(chatRoomEntity).toChatRoom();
  }

  @Override
  public List<ChatRoom> readChatRooms(ChatUser viewer) {
    return chatRoomJpaRepository.findByParticipant(viewer.id(), ChatEntityStatus.ACTIVE).stream()
        .map(ChatRoomEntity::toChatRoom)
        .toList();
  }

  @Override
  public Optional<ChatRoom> readById(Long chatRoomId) {
    return chatRoomJpaRepository.findById(chatRoomId).map(ChatRoomEntity::toChatRoom);
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
  public Long countUnreadMessages(ChatUser user) {
    return chatRoomJpaRepository.countUnreadMessagesByUserId(user.id());
  }
}
