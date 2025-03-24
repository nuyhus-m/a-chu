package com.ssafy.s12p21d206.achu.domain;

public record ChatStatus(Long goodsId, Long chatCount) {

  public Long getGoodsId() {
    return goodsId;
  }

  public Long getChatCount() {
    return chatCount;
  }
}
