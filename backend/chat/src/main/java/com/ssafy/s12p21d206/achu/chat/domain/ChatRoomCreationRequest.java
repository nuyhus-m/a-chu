package com.ssafy.s12p21d206.achu.chat.domain;

public record ChatRoomCreationRequest(Long receiverId) {
  public static ChatRoomCreationRequest of(Long receiverId) {
    return new ChatRoomCreationRequest(receiverId);
  }
}
