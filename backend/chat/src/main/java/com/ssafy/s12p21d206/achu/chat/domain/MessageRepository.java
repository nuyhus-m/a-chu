package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;

public interface MessageRepository {

  Message save(ChatUser sender, ChatRoom chatRoom, NewMessage newMessage);

  List<Message> readLastMessagesIn(List<ChatRoom> chatRooms);

  List<Message> findMessagesByChatRoomId(Long chatRoomId);
}
