package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;
import java.util.Map;

public record UnreadCountResponse(long totalUnread, Map<Long, Long> roomUnread) {
  public static UnreadCountResponse from(UnreadCount unreadCount) {
    return new UnreadCountResponse(unreadCount.totalUnread(), unreadCount.roomUnread());
  }
}
