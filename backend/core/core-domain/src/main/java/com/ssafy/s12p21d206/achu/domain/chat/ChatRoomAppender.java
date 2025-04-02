package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomAppender {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomAppender(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public ChatRoom append(User user, NewChatRoom newChatRoom) {
    return chatRoomRepository.save(user, newChatRoom);
  }
}
