package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;

public record UnreadCountResponse(Long unreadMessageCount) {
  public static UnreadCountResponse of(UnreadCount unreadCount) {
    return new UnreadCountResponse(unreadCount.unreadCount());
  }
}
