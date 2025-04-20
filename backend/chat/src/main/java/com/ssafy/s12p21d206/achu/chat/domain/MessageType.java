package com.ssafy.s12p21d206.achu.chat.domain;

public enum MessageType {
  TEXT,
  LEFT;

  public boolean isUserMessage() {
    return !this.equals(LEFT);
  }
}
