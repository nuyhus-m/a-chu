package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.GoodsDetail;
import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;
import java.util.List;

public record TradeHistoryResponse(
    Long id, TradeStatus tradeStatus, String title, String imgUrl, Long price) {
  public static List<TradeHistoryResponse> of(List<GoodsDetail> goodsDetails) {
    return goodsDetails.stream()
        .map(goodsDetail -> new TradeHistoryResponse(
            goodsDetail.id(),
            goodsDetail.tradeStatus(),
            goodsDetail.title(),
            goodsDetail.imgUrls().get(0),
            goodsDetail.price()))
        .toList();
  }
}
