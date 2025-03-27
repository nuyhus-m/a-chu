package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TradeHistoryReader {
  private final TradeHistoryRepository tradeHistoryRepository;

  public TradeHistoryReader(TradeHistoryRepository tradeHistoryRepository) {
    this.tradeHistoryRepository = tradeHistoryRepository;
  }

  public List<Long> findPurchaseHistories(User user) {
    List<TradeHistory> purchaseHistories = tradeHistoryRepository.findByBuyerId(user);
    return purchaseHistories.stream().map(TradeHistory::goodsId).toList();
  }

  public List<Long> findSaleHistories(User user) {
    List<TradeHistory> saleHistories = tradeHistoryRepository.findBySellerId(user);
    return saleHistories.stream().map(TradeHistory::goodsId).toList();
  }
}
