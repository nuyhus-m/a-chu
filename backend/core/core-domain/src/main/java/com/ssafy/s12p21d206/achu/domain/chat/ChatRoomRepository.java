package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import java.util.List;

public interface ChatRoomRepository {
  List<ChatRoom> findByUser(User user);

  ChatRoom save(User buyer, NewChatRoom newChatRoom);
}
