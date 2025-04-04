package com.ssafy.s12p21d206.achu.chat.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatUserRepository {

  Optional<ChatUserProfile> findProfile(ChatUser user);

  Optional<ChatUserProfile> findProfileById(Long userId);

  boolean exists(ChatUser chatUser);

  List<ChatUserProfile> findProfilesIn(Set<ChatUser> chatUsers);
}
