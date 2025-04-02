package com.ssafy.s12p21d206.achu.api.controller.chat;

import com.ssafy.s12p21d206.achu.domain.chat.NewChatRoom;

public record AppendChatRoomRequest(Long goodsId, Long sellerId) {
  public NewChatRoom toNewChatRoom() {
    return new NewChatRoom(goodsId, sellerId);
  }
}
