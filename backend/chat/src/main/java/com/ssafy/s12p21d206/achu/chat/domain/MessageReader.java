package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageReader {

  private final MessageRepository messageRepository;
  private final ChatRoomValidator chatRoomValidator;

  public MessageReader(MessageRepository messageRepository, ChatRoomValidator chatRoomValidator) {
    this.messageRepository = messageRepository;
    this.chatRoomValidator = chatRoomValidator;
  }

  public List<Message> readMessagesByChatRoomId(ChatUser viewer, Long chatRoomId) {
    chatRoomValidator.validateParticipant(chatRoomId, viewer);
    return messageRepository.findMessagesByChatRoomId(chatRoomId);
  }
}
