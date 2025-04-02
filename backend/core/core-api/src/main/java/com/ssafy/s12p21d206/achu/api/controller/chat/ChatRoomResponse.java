package com.ssafy.s12p21d206.achu.api.controller.chat;

import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.domain.chat.ChatRoom;
import java.util.List;

public record ChatRoomResponse(Long id, Long goodsId, Long partnerId
    //        String goodsTitle,
    //        String goodsThumbnailUrl,
    //        String partnerNickname,
    //        String lastMessage,
    //        LocalDateTime lastMessageTime,
    //        int unreadMessageCount

    ) {

  public static ChatRoomResponse from(ChatRoom chatRoom, User user) {

    Long partnerId;
    if (chatRoom.getBuyerId().equals(user.id())) {
      partnerId = chatRoom.getSellerId();
    } else {
      partnerId = chatRoom.getBuyerId();
    }

    return new ChatRoomResponse(chatRoom.getId(), chatRoom.getGoodsId(), partnerId);
  }

  public static List<ChatRoomResponse> of(List<ChatRoom> chatRooms, User user) {
    return chatRooms.stream()
        .map(chatRoom -> ChatRoomResponse.from(chatRoom, user))
        .toList();
  }
}
