package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomSummary;
import java.time.LocalDateTime;
import java.util.List;

public record ChatRoomResponse(
    Long roomId,
    Long partnerId,
    String partnerNickname,
    String partnerProfileUrl,
    String lastMessage,
    LocalDateTime lastMessageTime,
    long unreadCount,
    boolean partnerActive) {
  public static ChatRoomResponse from(ChatRoomSummary summary) {
    return new ChatRoomResponse(
        summary.roomId(),
        summary.partnerId(),
        summary.partnerNickname(),
        summary.partnerProfileUrl(),
        summary.lastMessage(),
        summary.lastMessageTime(),
        summary.unreadCount(),
        summary.partnerActive());
  }

  public static List<ChatRoomResponse> from(List<ChatRoomSummary> summaries) {
    return summaries.stream().map(ChatRoomResponse::from).toList();
  }
}
