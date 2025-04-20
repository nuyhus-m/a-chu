package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class TradeAppender {

  private final TradeRepository tradeRepository;

  public TradeAppender(TradeRepository tradeRepository) {
    this.tradeRepository = tradeRepository;
  }

  public Trade completeTrade(User user, Long goodsId, NewTrade newTrade) {
    return tradeRepository.save(user, goodsId, newTrade);
  }
}
