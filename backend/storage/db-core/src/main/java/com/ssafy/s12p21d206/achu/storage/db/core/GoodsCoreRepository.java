package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Goods;
import com.ssafy.s12p21d206.achu.domain.GoodsRepository;
import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
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

  @Override
  public List<Goods> findGoods(User user, Long offset, Long limit, SortType sort) {
    Pageable pageable = PageRequest.of(offset.intValue(), limit.intValue(), convertSort(sort));
    List<GoodsEntity> goodsEntities =
        goodsJpaRepository.findAllByAndEntityStatus(pageable, EntityStatus.ACTIVE);
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

  private Sort convertSort(SortType sort) {
    return switch (sort) {
      case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
      case OLDEST -> Sort.by(Sort.Direction.ASC, "createdAt");
    };
  }
}
