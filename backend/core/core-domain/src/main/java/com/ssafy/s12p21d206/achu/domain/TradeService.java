package com.ssafy.s12p21d206.achu.domain;

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

  public TradeService(
      TradeAppender tradeAppender,
      TradeReader tradeReader,
      GoodsReader goodsReader,
      GoodsValidator goodsValidator,
      UserValidator userValidator,
      TradeValidator tradeValidator) {
    this.tradeAppender = tradeAppender;
    this.tradeReader = tradeReader;
    this.goodsReader = goodsReader;
    this.goodsValidator = goodsValidator;
    this.userValidator = userValidator;
    this.tradeValidator = tradeValidator;
  }

  public Trade completeTrade(User user, Long goodsId, NewTrade newTrade) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(goodsId, user.id());
    userValidator.validateExists(newTrade.buyerId());
    goodsValidator.validateIsSelling(goodsId);
    tradeValidator.validateSellerIsNotBuyer(user.id(), newTrade.buyerId());
    return tradeAppender.completeTrade(user, goodsId, newTrade);
  }

  public List<Goods> findTradedGoods(
      User user, TradeType tradeType, Long offset, Long limit, SortType sort) {
    List<Long> goodsIds = tradeReader.findTradedGoodsIds(user, tradeType, offset, limit, sort);
    return goodsReader.readGoodsByIds(goodsIds);
  }
}
