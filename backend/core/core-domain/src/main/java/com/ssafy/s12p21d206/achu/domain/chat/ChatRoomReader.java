package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomReader {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomReader(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public List<ChatRoom> readChatRooms(User user) {
    return chatRoomRepository.findByUser(user);
  }
}
