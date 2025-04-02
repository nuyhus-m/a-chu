package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.domain.chat.ChatRoom;
import com.ssafy.s12p21d206.achu.domain.chat.ChatRoomRepository;
import com.ssafy.s12p21d206.achu.domain.chat.NewChatRoom;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomCoreRepository implements ChatRoomRepository {

  private final ChatRoomJpaRepository chatRoomJpaRepository;

  public ChatRoomCoreRepository(ChatRoomJpaRepository chatRoomJpaRepository) {
    this.chatRoomJpaRepository = chatRoomJpaRepository;
  }

  @Override
  public List<ChatRoom> findByUser(User user) {
    return chatRoomJpaRepository.findByUserId(user.id()).stream()
        .map(ChatRoomEntity::toChatRoom)
        .toList();
  }

  @Override
  public ChatRoom save(User buyer, NewChatRoom newChatRoom) {
    return chatRoomJpaRepository
        .save(new ChatRoomEntity(newChatRoom.goodsId(), newChatRoom.sellerId(), buyer.id()))
        .toChatRoom();
  }
}
