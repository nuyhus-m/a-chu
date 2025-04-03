package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomDetail;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.util.List;
import java.util.Objects;

public record ChatRoomResponse(
    Long id,
    Long goodsId,
    Long buyerId,
    Long sellerId,
    boolean isSellerLeft,
    boolean isBuyerLeft,
    GoodsResponse goods,
    UserProfileResponse partner,
    MessageResponse lastMessage) {

  public static List<ChatRoomResponse> from(ChatUser viewer, List<ChatRoomDetail> chatRoomDetails) {
    return chatRoomDetails.stream()
        .map(chatRoomDetail -> ChatRoomResponse.from(viewer, chatRoomDetail))
        .toList();
  }

  public static ChatRoomResponse from(ChatUser viewer, ChatRoomDetail chatRoomDetail) {
    ChatUserProfile seller = chatRoomDetail.chatRoomWithParticipant().seller();
    ChatUserProfile buyer = chatRoomDetail.chatRoomWithParticipant().buyer();
    ChatUserProfile partner = Objects.equals(viewer.id(), seller.id()) ? buyer : seller;

    return new ChatRoomResponse(
        chatRoomDetail.chatRoomWithParticipant().chatRoom().id(),
        chatRoomDetail.chatRoomWithParticipant().chatRoom().goodsId(),
        chatRoomDetail.chatRoomWithParticipant().chatRoom().buyer().id(),
        chatRoomDetail.chatRoomWithParticipant().chatRoom().seller().id(),
        chatRoomDetail.chatRoomWithParticipant().chatRoom().participantStatus().isSellerLeft(),
        chatRoomDetail.chatRoomWithParticipant().chatRoom().participantStatus().isBuyerLeft(),
        GoodsResponse.from(chatRoomDetail.goods()),
        UserProfileResponse.from(partner),
        MessageResponse.from(chatRoomDetail.lastMessage()));
  }
}
