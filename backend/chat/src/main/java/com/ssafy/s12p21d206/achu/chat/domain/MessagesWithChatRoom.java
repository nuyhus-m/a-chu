package com.ssafy.s12p21d206.achu.chat.domain;

import java.util.List;

public record MessagesWithChatRoom(List<Message> messages, ChatRoom chatRoom) {}
