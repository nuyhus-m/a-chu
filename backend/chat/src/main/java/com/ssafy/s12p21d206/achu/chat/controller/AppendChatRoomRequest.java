package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.NewChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.NewMessage;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;

public record AppendChatRoomRequest(Long goodsId, Long sellerId, String content) {

  public NewChatRoom toNewChatRoom() {
    return new NewChatRoom(this.goodsId, new ChatUser(this.sellerId));
  }

  public NewMessage toNewMessage() {
    return new NewMessage(content, MessageType.TEXT);
  }
}
