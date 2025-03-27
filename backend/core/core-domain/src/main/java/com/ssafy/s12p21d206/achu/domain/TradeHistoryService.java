package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import com.ssafy.s12p21d206.achu.domain.support.TradeType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TradeHistoryService {
  private final TradeHistoryAppender tradeHistoryAppender;
  private final TradeHistoryReader tradeHistoryReader;
  private final GoodsReader goodsReader;
  private final GoodsValidator goodsValidator;
  private final UserValidator userValidator;
  private final PageValidator pageValidator;

  public TradeHistoryService(
      TradeHistoryAppender tradeHistoryAppender,
      TradeHistoryReader tradeHistoryReader,
      GoodsReader goodsReader,
      GoodsValidator goodsValidator,
      UserValidator userValidator,
      PageValidator pageValidator) {
    this.tradeHistoryAppender = tradeHistoryAppender;
    this.tradeHistoryReader = tradeHistoryReader;
    this.goodsReader = goodsReader;
    this.goodsValidator = goodsValidator;
    this.userValidator = userValidator;
    this.pageValidator = pageValidator;
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

  public List<GoodsDetail> findTradeHistoryGoods(
      User user, TradeType tradeType, Long offset, Long limit, SortType sort) {
    pageValidator.validatePageParams(offset, limit);
    List<Long> goodsIds =
        switch (tradeType) {
          case PURCHASE -> tradeHistoryReader.findPurchaseHistories(user);
          case SALE -> tradeHistoryReader.findSaleHistories(user);
          default -> throw new CoreException(CoreErrorType.INVALID_TRADE_TYPE);
        };
    return goodsReader.readGoodsDetails(goodsIds, offset, limit, sort);
  }
}
