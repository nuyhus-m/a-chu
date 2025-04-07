package com.ssafy.s12p21d206.achu;

import java.util.Set;
import org.springframework.context.ApplicationEvent;

public class PriceChangeChatEvent extends ApplicationEvent {
  private final Set<Long> chatUserIds;
  private final Long goodsId;
  private final String goodsTitle;

  public PriceChangeChatEvent(
      Object source, Set<Long> chatUserIds, Long goodsId, String goodsTitle) {
    super(source);
    this.chatUserIds = chatUserIds;
    this.goodsId = goodsId;
    this.goodsTitle = goodsTitle;
  }

  public Set<Long> getChatUserIds() {
    return chatUserIds;
  }

  public Long getGoodsId() {
    return goodsId;
  }

  public String getGoodsTitle() {
    return goodsTitle;
  }
}
