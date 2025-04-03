package com.ssafy.s12p21d206.achu.chat.domain.user;

public record ChatUser(Long id) {
  public static ChatUser of(Long id) {
    return new ChatUser(id);
  }
}
