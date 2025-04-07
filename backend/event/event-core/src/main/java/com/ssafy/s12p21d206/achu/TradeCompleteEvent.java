package com.ssafy.s12p21d206.achu;

import org.springframework.context.ApplicationEvent;

public class TradeCompleteEvent extends ApplicationEvent {
  private final Long buyerId;
  private final Long tradeId;

  public TradeCompleteEvent(Object source, Long buyerId, Long tradeId) {
    super(source);
    this.buyerId = buyerId;
    this.tradeId = tradeId;
  }

  public Long getBuyerId() {
    return buyerId;
  }

  public Long getTradeId() {
    return tradeId;
  }
}
