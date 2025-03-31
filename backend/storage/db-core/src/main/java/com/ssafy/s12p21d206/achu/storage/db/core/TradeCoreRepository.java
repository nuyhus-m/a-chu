package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.*;
import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class TradeCoreRepository implements TradeRepository {
  private final TradeJpaRepository tradeJpaRepository;
  private final GoodsJpaRepository goodsJpaRepository;

  public TradeCoreRepository(
      TradeJpaRepository tradeJpaRepository, GoodsJpaRepository goodsJpaRepository) {
    this.tradeJpaRepository = tradeJpaRepository;
    this.goodsJpaRepository = goodsJpaRepository;
  }

  @Override
  public Trade save(User user, Long goodsId, NewTrade newTrade) {
    TradeEntity tradeEntity =
        tradeJpaRepository.save(new TradeEntity(goodsId, user.id(), newTrade.buyerId()));
    GoodsEntity goodsEntity = goodsJpaRepository
        .findById(goodsId)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND));
    goodsEntity.sold();
    goodsJpaRepository.save(goodsEntity);
    return tradeEntity.toTrade();
  }

  @Override
  public List<Trade> findTradedGoods(
      User user, TradeType tradeType, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<TradeEntity> tradeEntities = tradeJpaRepository.findByUserIdAndEntityStatus(
        user.id(), tradeType, pageable, EntityStatus.ACTIVE);
    return tradeEntities.stream().map(TradeEntity::toTrade).toList();
  }

  private Sort convertSort(SortType sort) {
    return switch (sort) {
      case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
      case OLDEST -> Sort.by(Sort.Direction.ASC, "createdAt");
    };
  }
}
