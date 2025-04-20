package com.ssafy.s12p21d206.achu;

import org.springframework.context.ApplicationEvent;

public class NewChatMessageEvent extends ApplicationEvent {

  private final Long userId;
  private final String senderNickname;
  private final Long chatRoomId;

  private final String content;

  public NewChatMessageEvent(
      Object source, Long userId, String senderNickname, Long chatRoomId, String content) {
    super(source);
    this.userId = userId;
    this.senderNickname = senderNickname;
    this.chatRoomId = chatRoomId;
    this.content = content;
  }

  public Long getUserId() {
    return userId;
  }

  public String getSenderNickname() {
    return senderNickname;
  }

  public Long getChatRoomId() {
    return chatRoomId;
  }

  public String getContent() {
    return content;
  }
}
