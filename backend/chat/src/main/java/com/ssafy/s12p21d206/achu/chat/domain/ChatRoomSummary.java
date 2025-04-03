package com.ssafy.s12p21d206.achu.chat.domain;

import java.time.LocalDateTime;

public record ChatRoomSummary(
    Long roomId,
    Long partnerId,
    String partnerNickname,
    String partnerProfileUrl,
    String lastMessage,
    LocalDateTime lastMessageTime,
    long unreadCount,
    boolean partnerActive) {}
