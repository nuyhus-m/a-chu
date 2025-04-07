package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.PriceChangeChatEvent;
import com.ssafy.s12p21d206.achu.PriceChangeLikeEvent;
import com.ssafy.s12p21d206.achu.TradeCompleteEvent;
import com.ssafy.s12p21d206.achu.TradeCompleteWisherEvent;
import java.util.Set;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GoodsEventPublisher {

  private final LikeReader likeReader;
  private final CoreChatRoomReader coreChatRoomReader;
  private final ApplicationEventPublisher eventPublisher;

  public GoodsEventPublisher(
      LikeReader likeReader,
      CoreChatRoomReader coreChatRoomReader,
      ApplicationEventPublisher eventPublisher) {
    this.likeReader = likeReader;
    this.coreChatRoomReader = coreChatRoomReader;
    this.eventPublisher = eventPublisher;
  }

  public void publishPriceChangeEvent(Goods goods, ModifyGoods modifyGoods) {
    if (!goods.isSamePrice(modifyGoods.price())) {
      Set<Long> likerIds = likeReader.readLikerIds(goods.id());
      Set<Long> chatUserIds = coreChatRoomReader.readChatUserIds(goods.id());

      likerIds.removeAll(chatUserIds);
      eventPublisher.publishEvent(
          new PriceChangeLikeEvent(this, likerIds, goods.id(), goods.title()));
      eventPublisher.publishEvent(
          new PriceChangeChatEvent(this, chatUserIds, goods.id(), goods.title()));
    }
  }

  public void publishTradeCompleteEvent(Trade trade, NewTrade newTrade) {
    eventPublisher.publishEvent(new TradeCompleteEvent(this, newTrade.buyerId(), trade.id()));
    Set<Long> likerIds = likeReader.readLikerIds(trade.goodsId());
    likerIds.removeAll(Set.of(trade.buyerId(), trade.sellerId()));
    eventPublisher.publishEvent(new TradeCompleteWisherEvent(this, likerIds, trade.goodsId()));
  }
}
