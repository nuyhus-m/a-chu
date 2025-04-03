package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomCreationRequest;

public record ChatRoomRequest(Long receiverId) {
  public ChatRoomCreationRequest toDomain() {
    return ChatRoomCreationRequest.of(receiverId);
  }
}
