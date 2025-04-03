package com.ssafy.s12p21d206.achu.chat.domain.user;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import org.springframework.stereotype.Component;

@Component
public class ChatUserReader {

  private final ChatUserRepository chatUserRepository;

  public ChatUserReader(ChatUserRepository chatUserRepository) {
    this.chatUserRepository = chatUserRepository;
  }

  public ChatUserProfile getUserProfile(ChatUser user) {
    return chatUserRepository
        .findProfile(user)
        .orElseThrow(() -> new ChatException(ChatErrorType.USER_PROFILE_NOT_FOUND));
  }
}
