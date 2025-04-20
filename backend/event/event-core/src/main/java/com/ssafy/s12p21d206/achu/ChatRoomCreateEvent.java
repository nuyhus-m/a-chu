package com.ssafy.s12p21d206.achu;

import org.springframework.context.ApplicationEvent;

public class ChatRoomCreateEvent extends ApplicationEvent {
  private final Long sellerId;

  private final Long chatRoomId;

  private final String goodsTitle;

  public ChatRoomCreateEvent(Object source, Long sellerId, Long chatRoomId, String goodsTitle) {
    super(source);
    this.sellerId = sellerId;
    this.chatRoomId = chatRoomId;
    this.goodsTitle = goodsTitle;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public Long getChatRoomId() {
    return chatRoomId;
  }

  public String getGoodsTitle() {
    return goodsTitle;
  }
}
