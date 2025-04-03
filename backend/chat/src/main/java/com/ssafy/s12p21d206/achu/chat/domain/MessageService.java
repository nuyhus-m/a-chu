package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageAppender messageAppender;
  private final MessageReader messageReader;
  private final ChatRoomReader chatRoomReader;

  public MessageService(
      MessageAppender messageAppender, MessageReader messageReader, ChatRoomReader chatRoomReader) {
    this.messageAppender = messageAppender;
    this.messageReader = messageReader;
    this.chatRoomReader = chatRoomReader;
  }

  public Message append(ChatUser sender, ChatRoom chatRoom, NewMessage newMessage) {
    return messageAppender.append(sender, chatRoom, newMessage);
  }

  public Message append(ChatUser sender, Long chatRoomId, NewMessage newMessage) {
    ChatRoom chatRoom = chatRoomReader.read(chatRoomId);
    return messageAppender.append(sender, chatRoom, newMessage);
  }

  public List<Message> readLastMessagesIn(List<ChatRoom> chatRooms) {
    return messageReader.readLastMessagesIn(chatRooms);
  }
}
