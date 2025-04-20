package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;

public interface TradeRepository {
  Trade save(User user, Long goodsId, NewTrade newTrade);

  List<Trade> findTradedGoods(
      User user, TradeType tradeType, Long offset, Long limit, SortType sort);
}
