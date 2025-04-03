package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomReader {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomReader(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public List<ChatRoom> readChatRooms(ChatUser viewer) {
    return chatRoomRepository.readChatRooms(viewer);
  }

  public ChatRoom read(Long chatRoomId) {
    return chatRoomRepository
        .readById(chatRoomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_ROOM_NOT_FOUND));
  }

  public Long countUnreadMessages(ChatUser user) {
    return chatRoomRepository.countUnreadMessages(user);
  }
}
