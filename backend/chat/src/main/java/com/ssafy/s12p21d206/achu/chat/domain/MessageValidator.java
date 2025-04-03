package com.ssafy.s12p21d206.achu.chat.domain;

import org.springframework.stereotype.Component;

@Component
public class MessageValidator {

  private final MessageRepository messageRepository;

  public MessageValidator(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }
}
