package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.TradeHistory;
import com.ssafy.s12p21d206.achu.domain.User;

public record AppendTradeHistoryRequest(Long goodsId, Long buyerId) {
  public static TradeHistory toTradeHistory(User user, AppendTradeHistoryRequest request) {
    return new TradeHistory(request.goodsId, user.id(), request.buyerId);
  }
}
