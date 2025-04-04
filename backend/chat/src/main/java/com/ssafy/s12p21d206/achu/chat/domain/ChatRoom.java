package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;

public record ChatRoom(
    Long id, Long goodsId, ChatUser seller, ChatUser buyer, ParticipantStatus participantStatus) {}
