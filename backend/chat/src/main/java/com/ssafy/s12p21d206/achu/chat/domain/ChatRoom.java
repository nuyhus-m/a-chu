package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.goods.Goods;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.util.Objects;

public record ChatRoom(
    Long id,
    Goods goods,
    ChatUserProfile seller,
    ChatUserProfile buyer,
    ParticipantStatus participantStatus,
    Message lastMessage) {

  public ChatRoom updateLastMessage(Message message) {
    return new ChatRoom(id, goods, seller, buyer, participantStatus, message);
  }

  public ChatUser findPartner(ChatUser user) {
    if (!Objects.equals(seller.id(), user.id()) || !(Objects.equals(buyer.id(), user.id()))) {
      throw new IllegalArgumentException("채팅 참여자가 아닙니다.");
    }

    return Objects.equals(seller.id(), user.id())
        ? new ChatUser(buyer.id())
        : new ChatUser(seller.id());
  }

  public boolean isParticipant(ChatUser user) {
    return this.seller.id().equals(user.id()) || this.buyer.id().equals(user.id());
  }
}
