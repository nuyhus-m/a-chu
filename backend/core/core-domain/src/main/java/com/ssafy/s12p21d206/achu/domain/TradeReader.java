package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TradeReader {
  private final TradeRepository tradeRepository;

  public TradeReader(TradeRepository tradeRepository) {
    this.tradeRepository = tradeRepository;
  }

  public List<Long> findTradedGoodsIds(
      User user, TradeType tradeType, Long offset, Long limit, SortType sort) {
    List<Trade> purchaseHistories =
        tradeRepository.findTradedGoods(user, tradeType, offset, limit, sort);
    return purchaseHistories.stream().map(Trade::goodsId).toList();
  }
}
