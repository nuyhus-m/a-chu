package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Service;

@Service
public class TradeHistoryService {
  private final TradeHistoryAppender tradeHistoryAppender;
  private final GoodsValidator goodsValidator;
  private final UserValidator userValidator;

  public TradeHistoryService(
      TradeHistoryAppender tradeHistoryAppender,
      GoodsValidator goodsValidator,
      UserValidator userValidator) {
    this.tradeHistoryAppender = tradeHistoryAppender;
    this.goodsValidator = goodsValidator;
    this.userValidator = userValidator;
  }

  public Long completeTrade(User user, TradeHistory tradeHistory) {
    goodsValidator.validateExists(tradeHistory.goodsId());
    goodsValidator.validateOwner(tradeHistory.goodsId(), user.id());
    userValidator.validateExists(tradeHistory.buyerId());
    goodsValidator.validateIsSelling(tradeHistory.goodsId());
    if (user.id().equals(tradeHistory.buyerId())) {
      throw new CoreException(CoreErrorType.SELLER_CANNOT_BE_BUYER);
    }
    return tradeHistoryAppender.completeTrade(user, tradeHistory);
  }
}
