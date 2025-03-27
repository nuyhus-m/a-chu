package com.ssafy.s12p21d206.achu.storage.db.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "tradeHistory")
@Entity
public class TradeHistoryEntity extends BaseEntity {
  private Long goodsId;

  private Long sellerId;

  private Long buyerId;

  protected TradeHistoryEntity() {}

  public TradeHistoryEntity(Long goodsId, Long sellerId, Long buyerId) {
    this.goodsId = goodsId;
    this.sellerId = sellerId;
    this.buyerId = buyerId;
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
