package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;

public interface GoodsRepository {
  List<Goods> findGoods(User user, Long offset, Long limit, SortType sort);

  List<Goods> findCategoryGoods(User user, Long categoryId, Long offset, Long limit, SortType sort);


  boolean existsById(Long goodsId);

  GoodsDetail findGoodsDetail(Long id);

  CategoryId findCategoryIdByGoodsId(Long id);

  User findUserIdByGoodsId(Long id);

  GoodsDetail save(User user, NewGoods newGoods);

  GoodsDetail modifyGoods(Long id, ModifyGoods modifyGoods);

  boolean existsByIdAndEntityStatus(Long id);

  boolean existsByIdAndUserIdAndEntityStatus(Long id, Long userId);

  boolean existByIdAndTradeStatus(Long id);

}
