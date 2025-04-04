package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

  private final ChatRoomAppender chatRoomAppender;
  private final ChatRoomReader chatRoomReader;

  public ChatRoomService(ChatRoomAppender chatRoomAppender, ChatRoomReader chatRoomReader) {
    this.chatRoomAppender = chatRoomAppender;
    this.chatRoomReader = chatRoomReader;
  }

  public ChatRoom append(ChatUser buyer, NewChatRoom newChatRoom) {
    return chatRoomAppender.append(buyer, newChatRoom);
  }

  public UnreadCount countUnreadMessages(ChatUser user) {
    return chatRoomReader.countUnreadMessages(user);
  }

  public List<ChatRoom> findChatRooms(ChatUser chatUser) {
    return chatRoomReader.readChatRooms(chatUser);
  }

  public Map<Long, UnreadCount> findUnreadCounts(ChatUser chatUser, List<Long> chatRoomIds) {
    return chatRoomReader.countUnreadMessages(chatUser, chatRoomIds);
  }
}
