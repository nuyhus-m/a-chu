package com.ssafy.s12p21d206.achu;

import java.util.Set;
import org.springframework.context.ApplicationEvent;

public class TradeCompleteWisherEvent extends ApplicationEvent {

  private final Set<Long> likerId;

  private final Long goodsId;

  public TradeCompleteWisherEvent(Object source, Set<Long> likerId, Long goodsId) {
    super(source);
    this.likerId = likerId;
    this.goodsId = goodsId;
  }

  public Set<Long> getLikerId() {
    return likerId;
  }

  public Long getGoodsId() {
    return goodsId;
  }
}
