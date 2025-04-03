package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.stereotype.Component;

@Component
public class MessageAppender {

  private final ChatRoomValidator chatRoomValidator;
  private final MessageRepository messageRepository;

  public MessageAppender(ChatRoomValidator chatRoomValidator, MessageRepository messageRepository) {
    this.chatRoomValidator = chatRoomValidator;
    this.messageRepository = messageRepository;
  }

  public Message append(ChatUser sender, ChatRoom chatRoom, NewMessage newMessage) {
    chatRoomValidator.validateParticipant(chatRoom, sender);
    return messageRepository.save(sender, chatRoom, newMessage);
  }
}
