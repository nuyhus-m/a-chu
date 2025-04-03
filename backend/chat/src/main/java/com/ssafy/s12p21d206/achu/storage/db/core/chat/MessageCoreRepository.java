package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageRepository;
import com.ssafy.s12p21d206.achu.chat.domain.NewMessage;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class MessageCoreRepository implements MessageRepository {

  private final MessageJpaRepository messageJpaRepository;

  public MessageCoreRepository(MessageJpaRepository messageJpaRepository) {
    this.messageJpaRepository = messageJpaRepository;
  }

  @Override
  public Message save(ChatUser sender, ChatRoom chatRoom, NewMessage newMessage) {
    return messageJpaRepository
        .save(MessageEntity.fromNewMessage(sender, chatRoom, newMessage))
        .toMessage();
  }

  @Override
  public List<Message> readLastMessagesIn(List<ChatRoom> chatRooms) {
    if (chatRooms == null || chatRooms.isEmpty()) {
      return List.of();
    }

    List<Long> chatRoomIds = chatRooms.stream().map(ChatRoom::id).collect(Collectors.toList());

    return messageJpaRepository.findLastMessagesInChatRoomIds(chatRoomIds).stream()
        .map(MessageEntity::toMessage)
        .collect(Collectors.toList());
  }
}
