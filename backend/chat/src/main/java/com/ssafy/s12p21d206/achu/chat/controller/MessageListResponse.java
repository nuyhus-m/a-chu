package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessagesWithChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Objects;

public record MessageListResponse(
    List<MessageResponse> messages,
    ChatRoomResponse chatRoom,
    boolean isUserSeller,
    Long partnerLastReadMessageId) {

  public static MessageListResponse from(ChatUser user, MessagesWithChatRoom messagesWithChatRoom) {
    List<Message> messages = messagesWithChatRoom.messages();
    ChatRoom chatRoom = messagesWithChatRoom.chatRoom();
    boolean isUserSeller = Objects.equals(chatRoom.seller().id(), user.id());
    return new MessageListResponse(
        MessageResponse.listFrom(messages, user),
        ChatRoomResponse.from(user, chatRoom),
        isUserSeller,
        isUserSeller
            ? chatRoom.participantStatus().buyerLastReadMessageId()
            : chatRoom.participantStatus().sellerLastReadMessageId());
  }
}
