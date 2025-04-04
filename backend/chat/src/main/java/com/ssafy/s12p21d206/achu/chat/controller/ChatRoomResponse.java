package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record ChatRoomResponse(
    Long id,
    boolean isPartnerLeft,
    UserProfileResponse partner,
    GoodsResponse goods,
    Long unreadCount,
    MessageResponse lastMessage) {

  public static List<ChatRoomResponse> from(
      ChatUser viewer, List<ChatRoom> chatRooms, Map<Long, UnreadCount> unreadCountMap) {
    return chatRooms.stream()
        .map(chatRoom -> ChatRoomResponse.from(viewer, chatRoom, unreadCountMap.get(chatRoom.id())))
        .toList();
  }

  public static ChatRoomResponse from(ChatUser viewer, ChatRoom chatRoom, UnreadCount unreadCount) {
    ChatUserProfile partner =
        Objects.equals(viewer.id(), chatRoom.seller().id()) ? chatRoom.buyer() : chatRoom.seller();
    boolean isPartnerLeft = Objects.equals(viewer.id(), chatRoom.seller().id())
        ? chatRoom.participantStatus().isBuyerLeft()
        : chatRoom.participantStatus().isSellerLeft();

    return new ChatRoomResponse(
        chatRoom.id(),
        isPartnerLeft,
        UserProfileResponse.from(partner),
        GoodsResponse.from(chatRoom.goods()),
        unreadCount.unreadCount(),
        MessageResponse.from(chatRoom.lastMessage(), viewer));
  }
}
