package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessagesWithParticipants;
import com.ssafy.s12p21d206.achu.chat.domain.Participants;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.util.List;

public record MessageListResponse(
    List<MessageResponse> messages, UserProfileResponse partner, boolean isUserSeller) {

  public static MessageListResponse from(
      ChatUser user, MessagesWithParticipants messagesWithParticipants) {
    List<Message> messages = messagesWithParticipants.messages();
    Participants participants = messagesWithParticipants.participants();
    ChatUserProfile partner = participants.getPartner(user);
    return new MessageListResponse(
        MessageResponse.listFrom(messages, user),
        UserProfileResponse.from(partner),
        participants.isSeller(user));
  }
}
