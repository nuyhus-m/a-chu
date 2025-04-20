package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;

public record ChatApiUser(Long id) {

  public ChatUser toChatUser() {
    return new ChatUser(this.id);
  }
}
