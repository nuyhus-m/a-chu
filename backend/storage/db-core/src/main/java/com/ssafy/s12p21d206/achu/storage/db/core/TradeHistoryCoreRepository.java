package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.TradeHistory;
import com.ssafy.s12p21d206.achu.domain.TradeHistoryRepository;
import com.ssafy.s12p21d206.achu.domain.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TradeHistoryCoreRepository implements TradeHistoryRepository {
  private final TradeHistoryJpaRepository tradeHistoryJpaRepository;

  public TradeHistoryCoreRepository(TradeHistoryJpaRepository tradeHistoryJpaRepository) {
    this.tradeHistoryJpaRepository = tradeHistoryJpaRepository;
  }

  @Override
  public Long save(User user, TradeHistory tradeHistory) {
    return tradeHistoryJpaRepository
        .save(new TradeHistoryEntity(tradeHistory.goodsId(), user.id(), tradeHistory.buyerId()))
        .getId();
  }

  @Override
  public List<TradeHistory> findBySellerId(User user) {
    return tradeHistoryJpaRepository.findBySellerIdAndEntityStatus(user.id(), EntityStatus.ACTIVE);
  }

  @Override
  public List<TradeHistory> findByBuyerId(User user) {
    return tradeHistoryJpaRepository.findByBuyerIdAndEntityStatus(user.id(), EntityStatus.ACTIVE);
  }
}
