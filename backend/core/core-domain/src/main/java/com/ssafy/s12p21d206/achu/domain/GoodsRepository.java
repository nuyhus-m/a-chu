package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;

public interface GoodsRepository {
  List<Goods> findGoods(User user, Long offset, Long limit, SortType sort);

  List<Goods> findCategoryGoods(User user, Long categoryId, Long offset, Long limit, SortType sort);

  List<Goods> searchGoods(User user, String keyword, Long offset, Long limit, SortType sort);

  List<Goods> searchCategoryGoods(
      User user, Long categoryId, String keyword, Long offset, Long limit, SortType sort);

  List<Goods> findGoodsByIds(List<Long> ids);

  GoodsDetail findGoodsDetail(Long id);

  GoodsDetail save(User user, NewGoods newGoods);

  GoodsDetail modifyGoods(Long id, ModifyGoods modifyGoods);

  boolean existsById(Long id);

  boolean existsByIdAndUserId(Long id, Long userId);

  boolean isSelling(Long id);

  Long delete(Long id);
}
