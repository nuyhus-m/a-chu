package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.time.LocalDateTime;

public record Message(
    Long id,
    Long chatRoomId,
    String content,
    ChatUser sender,
    MessageType type,
    LocalDateTime timestamp) {}
