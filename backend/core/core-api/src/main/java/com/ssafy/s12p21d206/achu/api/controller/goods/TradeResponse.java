package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.Goods;
import com.ssafy.s12p21d206.achu.domain.TradeStatus;
import java.util.List;

public record TradeResponse(
    Long id, TradeStatus tradeStatus, String title, String imgUrl, Long price) {
  public static List<TradeResponse> of(List<Goods> goods) {
    return goods.stream()
        .map(goods1 -> new TradeResponse(
            goods1.id(),
            goods1.tradeStatus(),
            goods1.title(),
            goods1.imgUrls().get(0),
            goods1.price()))
        .toList();
  }
}
