package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageAppender messageAppender;
  private final MessageReader messageReader;

  public MessageService(MessageAppender messageAppender, MessageReader messageReader) {
    this.messageAppender = messageAppender;
    this.messageReader = messageReader;
  }

  public Message append(ChatUser sender, ChatRoom chatRoom, NewMessage newMessage) {
    return messageAppender.append(sender, chatRoom, newMessage);
  }

  public List<Message> readLastMessagesIn(List<ChatRoom> chatRooms) {
    return messageReader.readLastMessagesIn(chatRooms);
  }
}
