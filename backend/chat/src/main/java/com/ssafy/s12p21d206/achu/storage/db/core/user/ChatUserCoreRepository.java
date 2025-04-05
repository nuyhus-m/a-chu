package com.ssafy.s12p21d206.achu.storage.db.core.user;

import com.ssafy.s12p21d206.achu.chat.domain.Participants;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserRepository;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import org.springframework.stereotype.Repository;

@Repository
public class ChatUserCoreRepository implements ChatUserRepository {

  private final ChatUserJpaRepository chatUserJpaRepository;

  public ChatUserCoreRepository(ChatUserJpaRepository chatUserJpaRepository) {
    this.chatUserJpaRepository = chatUserJpaRepository;
  }

  @Override
  public boolean exists(ChatUser chatUser) {
    return chatUserJpaRepository.existsByIdAndEntityStatus(chatUser.id(), ChatEntityStatus.ACTIVE);
  }

  @Override
  public Participants findParticipants(Long chatRoomId) {
    ChatUserEntity seller = chatUserJpaRepository
        .findSeller(chatRoomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.USER_PROFILE_NOT_FOUND));
    ChatUserEntity buyer = chatUserJpaRepository
        .findBuyer(chatRoomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.USER_PROFILE_NOT_FOUND));
    return new Participants(seller.toProfile(), buyer.toProfile());
  }
}
