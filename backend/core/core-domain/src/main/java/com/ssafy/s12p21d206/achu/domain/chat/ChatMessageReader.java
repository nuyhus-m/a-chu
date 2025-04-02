package com.ssafy.s12p21d206.achu.domain.chat;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageReader {

  private final ChatMessageRepository chatMessageRepository;

  public ChatMessageReader(ChatMessageRepository chatMessageRepository) {
    this.chatMessageRepository = chatMessageRepository;
  }

  public List<ChatMessage> readMessages(Long chatRoomId) {
    return chatMessageRepository.findByChatRoomId(chatRoomId);
  }
}
