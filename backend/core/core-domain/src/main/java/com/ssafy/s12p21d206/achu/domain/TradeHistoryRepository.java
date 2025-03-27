package com.ssafy.s12p21d206.achu.domain;

import java.util.List;

public interface TradeHistoryRepository {
  Long save(User user, TradeHistory tradeHistory);

  List<TradeHistory> findBySellerId(User user);

  List<TradeHistory> findByBuyerId(User user);
}
