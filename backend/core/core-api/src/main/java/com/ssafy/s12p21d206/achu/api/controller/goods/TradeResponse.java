package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.GoodsDetail;
import com.ssafy.s12p21d206.achu.domain.TradeStatus;
import java.util.List;

public record TradeResponse(
    Long id, TradeStatus tradeStatus, String title, String imgUrl, Long price) {
  public static List<TradeResponse> of(List<GoodsDetail> goodsDetails) {
    return goodsDetails.stream()
        .map(goodsDetail -> new TradeResponse(
            goodsDetail.id(),
            goodsDetail.tradeStatus(),
            goodsDetail.title(),
            goodsDetail.imgUrls().get(0),
            goodsDetail.price()))
        .toList();
  }
}
