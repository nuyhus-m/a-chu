package com.ssafy.s12p21d206.achu.storage.db.core.user;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserRepository;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        .findByIdAndEntityStatus(user.id(), ChatEntityStatus.ACTIVE)
        .map(ChatUserEntity::toProfile);
  }

  @Override
  public Optional<ChatUserProfile> findProfileById(Long userId) {
    return chatUserJpaRepository
        .findByIdAndEntityStatus(userId, ChatEntityStatus.ACTIVE)
        .map(ChatUserEntity::toProfile);
  }

  @Override
  public boolean exists(ChatUser chatUser) {
    return chatUserJpaRepository.existsById(chatUser.id());
  }

  @Override
  public List<ChatUserProfile> findProfilesIn(Set<ChatUser> chatUsers) {
    List<Long> userIds = chatUsers.stream().map(ChatUser::id).toList();
    return chatUserJpaRepository.findByUserIdsIn(userIds).stream()
        .map(ChatUserEntity::toProfile)
        .toList();
  }
}
