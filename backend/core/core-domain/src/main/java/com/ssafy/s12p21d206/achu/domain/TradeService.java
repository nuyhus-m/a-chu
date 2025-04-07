package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TradeService {
  private final TradeAppender tradeAppender;
  private final TradeReader tradeReader;
  private final GoodsReader goodsReader;
  private final GoodsValidator goodsValidator;
  private final UserValidator userValidator;
  private final TradeValidator tradeValidator;
  private final GoodsEventPublisher publishEvent;

  public TradeService(
      TradeAppender tradeAppender,
      TradeReader tradeReader,
      GoodsReader goodsReader,
      GoodsValidator goodsValidator,
      UserValidator userValidator,
      TradeValidator tradeValidator,
      GoodsEventPublisher publishEvent) {
    this.tradeAppender = tradeAppender;
    this.tradeReader = tradeReader;
    this.goodsReader = goodsReader;
    this.goodsValidator = goodsValidator;
    this.userValidator = userValidator;
    this.tradeValidator = tradeValidator;
    this.publishEvent = publishEvent;
  }

  public Trade completeTrade(User user, Long goodsId, NewTrade newTrade) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(goodsId, user.id());
    userValidator.validateExists(newTrade.buyerId());
    goodsValidator.validateIsSelling(goodsId);
    tradeValidator.validateSellerIsNotBuyer(user.id(), newTrade.buyerId());
    Trade trade = tradeAppender.completeTrade(user, goodsId, newTrade);
    publishEvent.publishTradeCompleteEvent(trade, newTrade);
    return trade;
  }

  public List<Goods> findTradedGoods(
      User user, TradeType tradeType, Long offset, Long limit, SortType sort) {
    List<Long> goodsIds =
        switch (tradeType) {
          case SALE -> goodsReader.readSaleGoodsIds(user, offset, limit, sort);
          case PURCHASE -> tradeReader.findTradedGoodsIds(user, tradeType, offset, limit, sort);
          default -> throw new CoreException(CoreErrorType.INVALID_TRADE_TYPE);
        };
    return goodsReader.readGoodsByIds(goodsIds);
  }
}
