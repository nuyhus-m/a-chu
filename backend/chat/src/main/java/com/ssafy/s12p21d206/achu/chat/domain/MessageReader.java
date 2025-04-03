package com.ssafy.s12p21d206.achu.chat.domain;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageReader {

  private final MessageRepository messageRepository;

  public MessageReader(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<Message> readLastMessagesIn(List<ChatRoom> chatRooms) {
    return messageRepository.readLastMessagesIn(chatRooms);
  }
}
