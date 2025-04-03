package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomUpdater {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomUpdater(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public void updateReadStatus(ChatUser user, Long roomId, Long messageId) {
    chatRoomRepository.updateLastReadMessageId(roomId, user, messageId);
  }
}
