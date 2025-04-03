package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;

public interface ChatRoomRepository {

  boolean existsBy(Long goodsId, ChatUser seller, ChatUser buyer);

  ChatRoom save(ChatUser buyer, NewChatRoom newChatRoom);

  List<ChatRoom> readChatRooms(ChatUser viewer);
}
