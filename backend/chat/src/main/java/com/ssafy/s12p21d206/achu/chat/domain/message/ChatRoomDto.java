package com.ssafy.s12p21d206.achu.chat.domain.message;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.util.Objects;

public record ChatRoomDto(
    Long id,
    boolean isPartnerLeft,
    UserProfileDto partner,
    GoodsDto goods,
    Long unreadCount,
    MessageDto lastMessage) {

  public static ChatRoomDto from(ChatUser viewer, ChatRoom chatRoom, UnreadCount unreadCount) {
    ChatUserProfile partner =
        Objects.equals(viewer.id(), chatRoom.seller().id()) ? chatRoom.buyer() : chatRoom.seller();
    boolean isPartnerLeft = Objects.equals(viewer.id(), chatRoom.seller().id())
        ? chatRoom.participantStatus().isBuyerLeft()
        : chatRoom.participantStatus().isSellerLeft();

    return new ChatRoomDto(
        chatRoom.id(),
        isPartnerLeft,
        UserProfileDto.from(partner),
        GoodsDto.from(chatRoom.goods()),
        unreadCount.unreadCount(),
        MessageDto.from(chatRoom.lastMessage(), viewer));
  }
}
