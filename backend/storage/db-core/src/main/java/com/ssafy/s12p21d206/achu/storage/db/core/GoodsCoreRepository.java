package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.*;
import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsCoreRepository implements GoodsRepository {

  private final GoodsJpaRepository goodsJpaRepository;

  public GoodsCoreRepository(GoodsJpaRepository goodsJpaRepository) {
    this.goodsJpaRepository = goodsJpaRepository;
  }

  @Override
  public GoodsDetail save(User user, NewGoods newGoods) {
    return goodsJpaRepository
        .save(new GoodsEntity(
            newGoods.title(),
            newGoods.description(),
            newGoods.imgUrls(),
            TradeStatus.SELLING,
            newGoods.price(),
            newGoods.categoryId(),
            user.id(),
            newGoods.babyId()))
        .toGoodsDetail();
  }

  @Override
  public GoodsDetail modifyGoods(Long id, ModifyGoods modifyGoods) {
    GoodsEntity previousGoods = goodsJpaRepository
        .findByIdAndEntityStatus(id, EntityStatus.ACTIVE)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND));

    return goodsJpaRepository
        .save(new GoodsEntity(
            modifyGoods.title(),
            modifyGoods.description(),
            previousGoods.getImgUrls(),
            TradeStatus.SELLING,
            modifyGoods.price(),
            modifyGoods.categoryId(),
            previousGoods.getUserId(),
            modifyGoods.babyId()))
        .toGoodsDetail();
  }

  @Override
  public boolean existsByIdAndEntityStatus(Long id) {
    return goodsJpaRepository.existsByIdAndEntityStatus(id, EntityStatus.ACTIVE);
  }

  @Override
  public boolean existsByIdAndUserIdAndEntityStatus(Long id, Long userId) {
    return goodsJpaRepository.existsByIdAndUserIdAndEntityStatus(id, userId, EntityStatus.ACTIVE);
  }

  @Override
  public boolean existByIdAndTradeStatus(Long id) {
    return goodsJpaRepository.existsByIdAndTradeStatus(id, TradeStatus.SELLING);
  }

  @Override
  public Long delete(Long id) {
    GoodsEntity goods = goodsJpaRepository
        .findByIdAndEntityStatus(id, EntityStatus.ACTIVE)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND));
    goods.delete();
    return goods.getId();
  }

  @Override
  public List<Goods> findGoods(User user, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<GoodsEntity> goodsEntities = goodsJpaRepository.findByTradeStatusAndEntityStatus(
        pageable, TradeStatus.SELLING, EntityStatus.ACTIVE);
    return goodsEntities.stream().map(GoodsEntity::toGoods).toList();
  }

  @Override
  public List<Goods> findCategoryGoods(
      User user, Long categoryId, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<GoodsEntity> goodsEntities =
        goodsJpaRepository.findByCategoryIdAndTradeStatusAndEntityStatus(
            categoryId, pageable, TradeStatus.SELLING, EntityStatus.ACTIVE);
    return goodsEntities.stream().map(GoodsEntity::toGoods).toList();
  }

  @Override


  public boolean existsById(Long goodsId) {
    return goodsJpaRepository.existsByIdAndEntityStatus(goodsId, EntityStatus.ACTIVE);

  public List<GoodsDetail> findGoodsDetails(
      List<Long> ids, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    Page<GoodsEntity> goodsEntityPage =
        goodsJpaRepository.findByIdInAndEntityStatus(ids, EntityStatus.ACTIVE, pageable);
    return goodsEntityPage.stream().map(GoodsEntity::toGoodsDetail).toList();
  }

  @Override
  public GoodsDetail findGoodsDetail(Long id) {
    GoodsEntity goodsEntity = goodsJpaRepository
        .findByIdAndEntityStatus(id, EntityStatus.ACTIVE)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND));
    return goodsEntity.toGoodsDetail();
  }

  @Override
  public List<Goods> searchGoods(
      User user, String keyword, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<GoodsEntity> goodsEntities =
        goodsJpaRepository.findByTitleContainingAndTradeStatusAndEntityStatus(
            keyword, pageable, TradeStatus.SELLING, EntityStatus.ACTIVE);
    return goodsEntities.stream().map(GoodsEntity::toGoods).toList();
  }

  @Override
  public List<Goods> searchCategoryGoods(
      User user, Long categoryId, String keyword, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<GoodsEntity> goodsEntities =
        goodsJpaRepository.findByCategoryIdAndTitleContainingAndTradeStatusAndEntityStatus(
            categoryId, keyword, pageable, TradeStatus.SELLING, EntityStatus.ACTIVE);

    return goodsEntities.stream().map(GoodsEntity::toGoods).toList();
  }

  @Override
  public User findUserIdByGoodsId(Long id) {
    GoodsEntity goodsEntity = goodsJpaRepository
        .findById(id)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND));
    return goodsEntity.toUserId();
  }

  private Sort convertSort(SortType sort) {
    return switch (sort) {
      case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
      case OLDEST -> Sort.by(Sort.Direction.ASC, "createdAt");
    };
  }
}
