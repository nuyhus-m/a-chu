package com.ssafy.s12p21d206.achu.chat.storage.db.core.user;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserRepository;
import com.ssafy.s12p21d206.achu.chat.storage.db.core.support.ChatEntityStatus;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ChatUserCoreRepository implements ChatUserRepository {

  private final ChatUserJpaRepository chatUserJpaRepository;

  public ChatUserCoreRepository(ChatUserJpaRepository chatUserJpaRepository) {
    this.chatUserJpaRepository = chatUserJpaRepository;
  }

  @Override
  public Optional<ChatUserProfile> findProfile(ChatUser user) {
    return chatUserJpaRepository
        .findByIdAndStatus(user.id(), ChatEntityStatus.ACTIVE)
        .map(ChatUserEntity::toProfile);
  }
}
