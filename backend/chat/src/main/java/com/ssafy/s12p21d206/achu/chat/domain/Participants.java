package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.util.Objects;

public record Participants(ChatUserProfile seller, ChatUserProfile buyer) {

  public ChatUserProfile getPartner(ChatUser user) {
    if (Objects.equals(user.id(), seller.id())) {
      return buyer;
    } else if (Objects.equals(user.id(), buyer.id())) {
      return seller;
    }

    throw new IllegalArgumentException("user가 참여자가 아닙니다.");
  }
}
