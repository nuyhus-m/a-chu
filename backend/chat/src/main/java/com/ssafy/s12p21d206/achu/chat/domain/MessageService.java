package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  private final MessageAppender messageAppender;
  private final MessageReader messageReader;
  private final ChatRoomReader chatRoomReader;
  private final MessageReadHandler messageReadHandler;
  private final MessageEventNotifier messageEventNotifier;

  public MessageService(
      MessageAppender messageAppender,
      MessageReader messageReader,
      ChatRoomReader chatRoomReader,
      MessageReadHandler messageReadHandler,
      MessageEventNotifier messageEventNotifier) {
    this.messageAppender = messageAppender;
    this.messageReader = messageReader;
    this.chatRoomReader = chatRoomReader;
    this.messageReadHandler = messageReadHandler;
    this.messageEventNotifier = messageEventNotifier;
  }

  public Message append(ChatUser sender, ChatRoom chatRoom, NewMessage newMessage) {
    Message message = messageAppender.append(sender, chatRoom, newMessage);
    ChatUser partner = chatRoom.findPartner(sender);
    messageEventNotifier.notifyMessageArrived(partner, message);
    messageEventNotifier.notifyChatRoomUpdate(sender, partner, chatRoom, message);
    return message;
  }

  public Message append(ChatUser sender, Long chatRoomId, NewMessage newMessage) {
    ChatRoom chatRoom = chatRoomReader.read(chatRoomId);
    return this.append(sender, chatRoom, newMessage);
  }

  public void updateRead(ChatUser chatUser, Long roomId, Long lastReadMessageId) {
    messageReadHandler.updateRead(chatUser, roomId, lastReadMessageId);
  }

  public List<Message> getMessagesByChatRoomId(ChatUser viewer, Long chatRoomId) {
    return messageReader.readMessagesByChatRoomId(viewer, chatRoomId);
  }
}
