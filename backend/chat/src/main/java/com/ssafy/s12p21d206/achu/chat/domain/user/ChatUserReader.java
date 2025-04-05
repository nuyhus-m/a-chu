package com.ssafy.s12p21d206.achu.chat.domain.user;

import com.ssafy.s12p21d206.achu.chat.domain.Participants;
import org.springframework.stereotype.Component;

@Component
public class ChatUserReader {

  private final ChatUserRepository chatUserRepository;

  public ChatUserReader(ChatUserRepository chatUserRepository) {
    this.chatUserRepository = chatUserRepository;
  }

  public Participants readParticipants(Long chatRoomId) {
    return chatUserRepository.findParticipants(chatRoomId);
  }
}
