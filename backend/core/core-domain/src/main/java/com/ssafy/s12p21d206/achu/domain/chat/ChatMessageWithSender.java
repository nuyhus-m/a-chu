package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.UserDetail;

public record ChatMessageWithSender(ChatMessage chatMessage, UserDetail sender) {}
