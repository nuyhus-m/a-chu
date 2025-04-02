package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

  private final ChatRoomReader chatRoomReader;
  private final ChatRoomAppender chatRoomAppender;

  public ChatRoomService(ChatRoomReader chatRoomReader, ChatRoomAppender chatRoomAppender) {
    this.chatRoomReader = chatRoomReader;
    this.chatRoomAppender = chatRoomAppender;
  }

  public List<ChatRoom> findMyRooms(User user) {
    return chatRoomReader.readChatRooms(user);
  }

  public Long append(User user, NewChatRoom newChatRoom) {
    ChatRoom chatRoom = chatRoomAppender.append(user, newChatRoom);
    return chatRoom.getId();
  }
}
