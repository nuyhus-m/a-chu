package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import java.util.List;

public interface ChatMessageRepository {
  ChatMessage save(User user, Long chatRoomId, NewChatMessage newChatMessage);

  List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
