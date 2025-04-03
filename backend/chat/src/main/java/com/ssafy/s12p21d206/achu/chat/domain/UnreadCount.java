package com.ssafy.s12p21d206.achu.chat.domain;

import java.util.Map;

public record UnreadCount(long totalUnread, Map<Long, Long> roomUnread) {
  public static UnreadCount of(long totalUnread, Map<Long, Long> roomUnread) {
    return new UnreadCount(totalUnread, roomUnread);
  }

  public static UnreadCount empty() {
    return new UnreadCount(0, Map.of());
  }
}
