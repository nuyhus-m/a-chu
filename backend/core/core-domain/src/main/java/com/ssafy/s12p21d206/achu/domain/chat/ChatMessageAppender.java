package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageAppender {

  private final ChatMessageRepository chatMessageRepository;

  public ChatMessageAppender(ChatMessageRepository chatMessageRepository) {
    this.chatMessageRepository = chatMessageRepository;
  }

  public ChatMessage append(User user, Long chatRoomId, NewChatMessage newChatMessage) {
    return chatMessageRepository.save(user, chatRoomId, newChatMessage);
  }
}
