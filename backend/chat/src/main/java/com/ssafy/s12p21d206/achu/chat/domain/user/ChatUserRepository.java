package com.ssafy.s12p21d206.achu.chat.domain.user;

import java.util.Optional;

public interface ChatUserRepository {

  Optional<ChatUserProfile> findProfile(ChatUser user);
}
