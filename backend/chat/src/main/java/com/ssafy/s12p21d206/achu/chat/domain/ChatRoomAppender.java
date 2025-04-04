package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.goods.ChatGoodsValidator;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserValidator;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomAppender {

  private final ChatUserValidator chatUserValidator;
  private final ChatGoodsValidator chatGoodsValidator;
  private final ChatRoomValidator chatRoomValidator;
  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomAppender(
      ChatUserValidator chatUserValidator,
      ChatGoodsValidator chatGoodsValidator,
      ChatRoomValidator chatRoomValidator,
      ChatRoomRepository chatRoomRepository) {
    this.chatUserValidator = chatUserValidator;
    this.chatGoodsValidator = chatGoodsValidator;
    this.chatRoomValidator = chatRoomValidator;
    this.chatRoomRepository = chatRoomRepository;
  }

  public ChatRoom append(ChatUser buyer, NewChatRoom newChatRoom) {
    chatGoodsValidator.validateExists(newChatRoom.goodsId());
    chatUserValidator.validateExists(newChatRoom.seller());
    chatRoomValidator.validateExists(newChatRoom.goodsId(), newChatRoom.seller(), buyer);
    return chatRoomRepository.save(buyer, newChatRoom);
  }
}
