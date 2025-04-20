package com.ssafy.s12p21d206.achu;

import java.util.Set;
import org.springframework.context.ApplicationEvent;

public class PriceChangeLikeEvent extends ApplicationEvent {
  private final Set<Long> likerIds;
  private final Long goodsId;
  private final String goodsTitle;

  public PriceChangeLikeEvent(Object source, Set<Long> likerIds, Long goodsId, String goodsTitle) {
    super(source);
    this.likerIds = likerIds;
    this.goodsId = goodsId;
    this.goodsTitle = goodsTitle;
  }

  public Set<Long> getLikerIds() {
    return likerIds;
  }

  public Long getGoodsId() {
    return goodsId;
  }

  public String getGoodsTitle() {
    return goodsTitle;
  }
}
