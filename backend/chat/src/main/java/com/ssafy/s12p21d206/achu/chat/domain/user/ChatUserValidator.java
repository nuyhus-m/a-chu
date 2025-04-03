package com.ssafy.s12p21d206.achu.chat.domain.user;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import org.springframework.stereotype.Component;

@Component
public class ChatUserValidator {

  private final ChatUserRepository chatUserRepository;

  public ChatUserValidator(ChatUserRepository chatUserRepository) {
    this.chatUserRepository = chatUserRepository;
  }

  public void validateExists(ChatUser chatUser) {
    if (!chatUserRepository.exists(chatUser)) {
      throw new ChatException(ChatErrorType.USER_PROFILE_NOT_FOUND);
    }
  }
}
