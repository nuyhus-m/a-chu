package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;

public record ChatMessage(
    Long id, Long chatRoomId, Long senderId, String message, DefaultDateTime defaultDateTime) {}
