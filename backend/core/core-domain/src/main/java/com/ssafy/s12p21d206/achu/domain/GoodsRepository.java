package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;

public interface GoodsRepository {
  List<Goods> findGoods(User user, Long offset, Long limit, SortType sort);

  List<Goods> findCategoryGoods(User user, Long categoryId, Long offset, Long limit, SortType sort);
}
