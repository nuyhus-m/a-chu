package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;

public record ChatRoomWithParticipant(
    ChatRoom chatRoom, ChatUserProfile seller, ChatUserProfile buyer) {}
