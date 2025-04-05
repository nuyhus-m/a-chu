package com.ssafy.s12p21d206.achu.chat.domain.user;

import com.ssafy.s12p21d206.achu.chat.domain.Participants;

public interface ChatUserRepository {

  boolean exists(ChatUser chatUser);

  Participants findParticipants(Long chatRoomId);
}
