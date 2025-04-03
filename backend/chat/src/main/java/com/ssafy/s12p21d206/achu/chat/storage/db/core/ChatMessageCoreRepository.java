package com.ssafy.s12p21d206.achu.chat.storage.db.core;

import com.ssafy.s12p21d206.achu.chat.domain.ChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.ChatMessageRepository;
import com.ssafy.s12p21d206.achu.chat.domain.NewChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageCoreRepository implements ChatMessageRepository {

  private final ChatMessageJpaRepository messageJpaRepository;

  public ChatMessageCoreRepository(ChatMessageJpaRepository messageJpaRepository) {
    this.messageJpaRepository = messageJpaRepository;
  }

  @Override
  public ChatMessage save(NewChatMessage message) {
    ChatMessageEntity entity = ChatMessageEntity.fromNewChatMessage(message);
    ChatMessageEntity savedEntity = messageJpaRepository.save(entity);
    return savedEntity.toChatMessage();
  }

  @Override
  public Optional<ChatMessage> findById(Long messageId) {
    return messageJpaRepository.findById(messageId).map(ChatMessageEntity::toChatMessage);
  }

  @Override
  public List<ChatMessage> findByRoomIdBeforeId(Long roomId, Long before, Integer limit) {
    int size = limit != null ? limit : 100;
    Pageable pageable = PageRequest.of(0, size);
    return messageJpaRepository.findByRoomIdBeforeId(roomId, before, pageable).stream()
        .map(ChatMessageEntity::toChatMessage)
        .collect(Collectors.toList());
  }

  @Override
  public List<ChatMessage> findLatestByRoomIds(List<Long> roomIds) {
    return messageJpaRepository.findLatestByRoomIds(roomIds).stream()
        .map(ChatMessageEntity::toChatMessage)
        .collect(Collectors.toList());
  }

  @Override
  public long countUnreadMessages(Long roomId, ChatUser userId, Long lastReadMessageId) {
    if (lastReadMessageId == null) {
      return messageJpaRepository.count();
    }
    return messageJpaRepository.countUnreadMessages(roomId, lastReadMessageId);
  }

  @Override
  public boolean existsById(Long messageId) {
    return false;
  }
}
