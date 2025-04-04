package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.Objects;

public record ChatRoom(
    Long id, Long goodsId, ChatUser seller, ChatUser buyer, ParticipantStatus participantStatus) {

  public ChatUser findPartner(ChatUser user) {
    if (!Objects.equals(seller, user) || !(Objects.equals(buyer, user))) {
      throw new IllegalArgumentException("채팅 참여자가 아닙니다.");
    }

    return Objects.equals(seller, user) ? buyer : seller;
  }

  public boolean isParticipant(ChatUser user) {
    return this.seller.equals(user) || this.buyer.equals(user);
  }
}
