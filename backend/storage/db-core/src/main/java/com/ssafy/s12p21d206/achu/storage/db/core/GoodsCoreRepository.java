package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.*;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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

  private static final String GOODS_NOT_FOUND_MESSAGE = "존재하지 않는 상품입니다.";

  @Override
  public List<Goods> findGoods(User user, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<GoodsEntity> goodsEntities =
        goodsJpaRepository.findAllByEntityStatus(pageable, EntityStatus.ACTIVE);
    return goodsEntities.stream().map(GoodsEntity::toGoods).toList();
  }

  @Override
  public List<Goods> findCategoryGoods(
      User user, Long categoryId, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<GoodsEntity> goodsEntities = goodsJpaRepository.findAllByCategoryIdAndEntityStatus(
        categoryId, pageable, EntityStatus.ACTIVE);
    return goodsEntities.stream().map(GoodsEntity::toGoods).toList();
  }

  @Override

  public boolean existsById(Long goodsId) {
    return goodsJpaRepository.existsByIdAndEntityStatus(goodsId, EntityStatus.ACTIVE);

  public GoodsDetail findGoodsDetail(Long id) {
    GoodsEntity goodsEntity = goodsJpaRepository
        .findByIdAndEntityStatus(id, EntityStatus.ACTIVE)
        .orElseThrow(() -> new EntityNotFoundException(GOODS_NOT_FOUND_MESSAGE));
    return goodsEntity.toGoodsDetail();
  }

  @Override
  public CategoryId findCategoryIdByGoodsId(Long id) {
    GoodsEntity goodsEntity = goodsJpaRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(GOODS_NOT_FOUND_MESSAGE));
    return goodsEntity.toCategoryId();
  }

  @Override
  public User findUserIdByGoodsId(Long id) {
    GoodsEntity goodsEntity = goodsJpaRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(GOODS_NOT_FOUND_MESSAGE));
    return goodsEntity.toUserId();
  }

  private Sort convertSort(SortType sort) {
    return switch (sort) {
      case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
      case OLDEST -> Sort.by(Sort.Direction.ASC, "createdAt");
    };
  }
}
