package com.ssafy.s12p21d206.achu.chat.storage.db.core;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomRepository;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatRoomCoreRepository implements ChatRoomRepository {

  private final ChatRoomJpaRepository chatRoomJpaRepository;

  public ChatRoomCoreRepository(ChatRoomJpaRepository chatRoomJpaRepository) {
    this.chatRoomJpaRepository = chatRoomJpaRepository;
  }

  @Override
  public ChatRoom save(ChatRoom chatRoom) {
    ChatRoomEntity entity = ChatRoomEntity.fromDomain(chatRoom);
    ChatRoomEntity savedEntity = chatRoomJpaRepository.save(entity);
    return savedEntity.toDomain();
  }

  @Override
  public Optional<ChatRoom> findById(Long roomId) {
    return chatRoomJpaRepository.findById(roomId).map(ChatRoomEntity::toDomain);
  }

  @Override
  public List<ChatRoom> findActiveByUserId(ChatUser user) {
    return chatRoomJpaRepository.findActiveByUserId(user.id()).stream()
        .map(ChatRoomEntity::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Long> findRoomIdByUsers(ChatUser user1, ChatUser user2) {
    return chatRoomJpaRepository.findRoomIdByUsers(user1.id(), user2.id());
  }

  @Override
  public Optional<ChatUser> findPartnerIdByRoomIdAndUserId(Long roomId, ChatUser user) {
    return chatRoomJpaRepository
        .findPartnerIdByRoomIdAndUserId(roomId, user.id())
        .map(ChatUser::new);
  }

  @Override
  public Optional<Long> findLastReadMessageIdByRoomIdAndUserId(Long roomId, ChatUser user) {
    return chatRoomJpaRepository.findLastReadMessageIdByRoomIdAndUserId(roomId, user.id());
  }

  @Override
  public boolean isActiveForUser(Long roomId, ChatUser user) {
    ChatRoomEntity entity = chatRoomJpaRepository
        .findById(roomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_ROOM_NOT_FOUND));

    if (entity.getUser1Id().equals(user.id())) {
      return entity.isUser1Active();
    } else if (entity.getUser2Id().equals(user.id())) {
      return entity.isUser2Active();
    } else {
      return false;
    }
  }

  @Override
  public boolean isUserInRoom(Long roomId, ChatUser user) {
    ChatRoomEntity entity = chatRoomJpaRepository
        .findById(roomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_ROOM_NOT_FOUND));

    return entity.getUser1Id().equals(user.id()) || entity.getUser2Id().equals(user.id());
  }

  @Override
  @Transactional
  public ChatRoom leaveRoom(Long roomId, ChatUser user) {
    ChatRoomEntity entity = chatRoomJpaRepository
        .findById(roomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_ROOM_NOT_FOUND));

    if (entity.getUser1Id().equals(user.id())) {
      entity.setUser1Active(false);
    } else if (entity.getUser2Id().equals(user.id())) {
      entity.setUser2Active(false);
    } else {
      throw new ChatException(ChatErrorType.USER_NOT_IN_CHAT_ROOM);
    }

    ChatRoomEntity savedEntity = chatRoomJpaRepository.save(entity);
    return savedEntity.toDomain();
  }

  @Override
  @Transactional
  public ChatRoom updateLastReadMessageId(Long roomId, ChatUser user, Long messageId) {
    ChatRoomEntity entity = chatRoomJpaRepository
        .findById(roomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_ROOM_NOT_FOUND));

    if (entity.getUser1Id().equals(user.id())) {
      entity.setUser1LastReadMessageId(messageId);
    } else if (entity.getUser2Id().equals(user.id())) {
      entity.setUser2LastReadMessageId(messageId);
    } else {
      throw new ChatException(ChatErrorType.USER_NOT_IN_CHAT_ROOM);
    }

    ChatRoomEntity savedEntity = chatRoomJpaRepository.save(entity);
    return savedEntity.toDomain();
  }
}
