package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomValidator {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomValidator(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public void validateExists(Long goodsId, ChatUser seller, ChatUser buyer) {
    if (chatRoomRepository.existsBy(goodsId, seller, buyer)) {
      throw new ChatException(ChatErrorType.CHAT_ROOM_ALREADY_EXISTS);
    }
  }

  public void validateParticipant(ChatRoom chatRoom, ChatUser user) {
    if (!chatRoom.seller().equals(user) && !chatRoom.buyer().equals(user)) {
      throw new ChatException((ChatErrorType.USER_NOT_IN_CHAT_ROOM));
    }
  }

  public void validateParticipant(Long chatRoomId, ChatUser user) {
    if (!chatRoomRepository.isParticipant(chatRoomId, user)) {
      throw new ChatException((ChatErrorType.USER_NOT_IN_CHAT_ROOM));
    }
  }
}
