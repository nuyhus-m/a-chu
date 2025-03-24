package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

  private final GoodsReader goodsReader;

  public GoodsService(GoodsReader goodsReader) {
    this.goodsReader = goodsReader;
  }

  public List<Goods> findGoods(User user, Long offset, Long limit, SortType sort) {
    return goodsReader.readGoods(user, offset, limit, sort);
  }

  public List<Goods> findCategoryGoods(
      User user, Long categoryId, Long offset, Long limit, SortType sort) {
    return goodsReader.readCategoryGoods(user, categoryId, offset, limit, sort);
  }
}
