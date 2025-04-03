package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.MessageType;

public enum RequestMessageType {
  CHAT,
  IMAGE;

  public MessageType toMessageType() {
    return MessageType.valueOf(this.name());
  }
}
