package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomReader {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomReader(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public List<ChatRoom> findActiveRooms(ChatUser user) {
    return chatRoomRepository.findActiveByUserId(user);
  }

  public Optional<ChatUser> findPartner(Long roomId, ChatUser user) {
    return chatRoomRepository.findPartnerIdByRoomIdAndUserId(roomId, user);
  }
}
