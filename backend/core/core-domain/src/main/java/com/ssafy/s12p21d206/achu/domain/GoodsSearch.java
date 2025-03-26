package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GoodsSearch {

  private final GoodsRepository goodsRepository;

  public GoodsSearch(GoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public List<Goods> searchGoods(
      User user, String keyword, Long offset, Long limit, SortType sort) {
    return goodsRepository.searchGoods(user, keyword, offset, limit, sort);
  }

  public List<Goods> searchCategoryGoods(
      User user, Long categoryId, String keyword, Long offset, Long limit, SortType sort) {
    return goodsRepository.searchCategoryGoods(user, categoryId, keyword, offset, limit, sort);
  }
}
