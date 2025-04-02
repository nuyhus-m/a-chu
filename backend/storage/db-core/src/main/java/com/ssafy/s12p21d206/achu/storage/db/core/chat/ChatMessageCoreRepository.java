package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.domain.chat.ChatMessage;
import com.ssafy.s12p21d206.achu.domain.chat.ChatMessageRepository;
import com.ssafy.s12p21d206.achu.domain.chat.NewChatMessage;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageCoreRepository implements ChatMessageRepository {

  private final ChatMessageJpaRepository chatMessageJpaRepository;

  public ChatMessageCoreRepository(ChatMessageJpaRepository chatMessageJpaRepository) {
    this.chatMessageJpaRepository = chatMessageJpaRepository;
  }

  @Override
  public ChatMessage save(User user, Long chatRoomId, NewChatMessage newChatMessage) {
    return chatMessageJpaRepository
        .save(
            new ChatMessageEntity(chatRoomId, newChatMessage.senderId(), newChatMessage.message()))
        .toChatMessage();
  }

  @Override
  public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
    return chatMessageJpaRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId).stream()
        .map(ChatMessageEntity::toChatMessage)
        .toList();
  }
}
