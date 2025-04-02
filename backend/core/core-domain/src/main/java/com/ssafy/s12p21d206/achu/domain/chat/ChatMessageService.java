package com.ssafy.s12p21d206.achu.domain.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.domain.UserDetail;
import com.ssafy.s12p21d206.achu.domain.UserFinder;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {

  private final ChatMessageAppender chatMessageAppender;
  private final ChatMessageReader chatMessageReader;
  private final UserFinder userFinder;

  public ChatMessageService(
      ChatMessageAppender chatMessageAppender,
      ChatMessageReader chatMessageReader,
      UserFinder userFinder) {
    this.chatMessageAppender = chatMessageAppender;
    this.chatMessageReader = chatMessageReader;
    this.userFinder = userFinder;
  }

  public ChatMessageWithSender append(User user, Long chatRoomId, NewChatMessage newChatMessage) {
    ChatMessage chatMessage = chatMessageAppender.append(user, chatRoomId, newChatMessage);
    UserDetail sender = userFinder.findUserDetail(user);
    return new ChatMessageWithSender(chatMessage, sender);
  }

  public List<ChatMessage> findMessages(User user, Long chatRoomId) {
    // TODO: chatRoom에 user가 참여중인지 검증
    return chatMessageReader.readMessages(chatRoomId);
  }
}
