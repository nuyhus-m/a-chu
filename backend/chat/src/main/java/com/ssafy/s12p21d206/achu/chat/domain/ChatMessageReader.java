package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageReader {

  private final ChatMessageRepository messageRepository;

  public ChatMessageReader(ChatMessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<ChatMessage> readBeforeId(Long roomId, Long before, Integer limit) {
    return messageRepository.findByRoomIdBeforeId(roomId, before, limit);
  }

  public ChatMessage readById(Long messageId) {
    return messageRepository
        .findById(messageId)
        .orElseThrow(() -> new ChatException(ChatErrorType.MESSAGE_NOT_IN_CHAT_ROOM));
  }
}
