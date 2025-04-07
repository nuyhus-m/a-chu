package com.ssafy.s12p21d206.achu.chat.domain.message;

import com.ssafy.s12p21d206.achu.chat.domain.goods.ChatGoodsTradeStatus;
import com.ssafy.s12p21d206.achu.chat.domain.goods.Goods;

public record GoodsDto(
    Long id, String title, String thumbnailImageUrl, Long price, ChatGoodsTradeStatus tradeStatus) {
  public static GoodsDto from(Goods goods) {
    return new GoodsDto(
        goods.id(), goods.title(), goods.thumbnailImageUrl(), goods.price(), goods.tradeStatus());
  }
}
