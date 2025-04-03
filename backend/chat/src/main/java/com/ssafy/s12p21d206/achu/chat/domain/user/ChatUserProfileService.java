package com.ssafy.s12p21d206.achu.chat.domain.user;

import org.springframework.stereotype.Service;

@Service
public class ChatUserProfileService {

  private final ChatUserReader chatUserReader;

  public ChatUserProfileService(ChatUserReader chatUserReader) {
    this.chatUserReader = chatUserReader;
  }

  public ChatUserProfile getUserProfile(ChatUser user) {
    return chatUserReader.getUserProfile(user);
  }
}
