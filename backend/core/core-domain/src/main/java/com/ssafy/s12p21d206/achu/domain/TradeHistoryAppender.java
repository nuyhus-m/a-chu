package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class TradeHistoryAppender {

  private final TradeHistoryRepository tradeHistoryRepository;

  public TradeHistoryAppender(TradeHistoryRepository tradeHistoryRepository) {
    this.tradeHistoryRepository = tradeHistoryRepository;
  }

  public Long completeTrade(User user, TradeHistory tradeHistory) {
    return tradeHistoryRepository.save(user, tradeHistory);
  }
}
