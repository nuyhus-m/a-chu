package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.goods.Goods;

public record ChatRoomDetail(
    ChatRoomWithParticipant chatRoomWithParticipant, Goods goods, Message lastMessage) {}
