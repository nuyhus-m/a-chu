package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.NewMessage;

public record AppendMessageRequest(String content, MessageType type) {

  public NewMessage toNewMessage() {
    return new NewMessage(content, type);
  }
}
