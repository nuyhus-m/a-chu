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

    // TODO: valiator 안에 들어가야할 로직

    // tradeHistoryValidator.validateSellerIsNotBuyer(user, tradeHistory)
    if (user.id().equals(tradeHistory.buyerId())) {
      throw new CoreException(CoreErrorType.SELLER_CANNOT_BE_BUYER);
    }
    return tradeHistoryAppender.completeTrade(user, tradeHistory);
  }

  public List<GoodsDetail> findTradeHistoryGoods(
      User user, TradeType tradeType, Long offset, Long limit, SortType sort) {
    pageValidator.validatePageParams(
        offset, limit); // TODO: 앞단에서 처리.. 근데 굳이 -1이나 이런거 넣어봤을 때 빈 리스트가 온다 그러면 예외 처리 안해도 상관 없을듯?
    // TODO: page갯수에 맞는 id들을 여기서 가져오고 뒷단에서는 id들에 in절써서 가져 오기만 하면 됨!
    List<Long> goodsIds =
        switch (tradeType) {
          case PURCHASE -> tradeHistoryReader.findPurchaseHistories(
              user); // TODO: findTradeHistories(user, tradeType) -> Where절로 처리
          case SALE -> tradeHistoryReader.findSaleHistories(user);
          default -> throw new CoreException(CoreErrorType.INVALID_TRADE_TYPE);
        };

    return goodsReader.readGoodsDetails(goodsIds, offset, limit, sort);
  }
}
