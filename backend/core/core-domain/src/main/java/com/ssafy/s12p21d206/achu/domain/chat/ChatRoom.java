package com.ssafy.s12p21d206.achu.domain.chat;

public class ChatRoom {
  private final Long id;
  private final Long goodsId;
  private final Long sellerId;
  private final Long buyerId;

  public ChatRoom(Long id, Long goodsId, Long sellerId, Long buyerId) {
    this.id = id;
    this.goodsId = goodsId;
    this.sellerId = sellerId;
    this.buyerId = buyerId;
  }

  public Long getId() {
    return id;
  }

  public Long getGoodsId() {
    return goodsId;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public Long getBuyerId() {
    return buyerId;
  }
}
