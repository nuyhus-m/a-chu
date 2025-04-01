package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GoodsReader {

  private final GoodsRepository goodsRepository;

  public GoodsReader(GoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public List<Goods> readGoods(User user, Long offset, Long limit, SortType sort) {
    return goodsRepository.findGoods(user, offset, limit, sort);
  }

  public List<Goods> readCategoryGoods(
      User user, Long categoryId, Long offset, Long limit, SortType sort) {
    return goodsRepository.findCategoryGoods(user, categoryId, offset, limit, sort);
  }

  public GoodsDetail readGoodsDetail(Long id) {
    return goodsRepository.findGoodsDetail(id);
  }

  public List<Goods> readGoodsByIds(List<Long> ids) {
    return goodsRepository.findGoodsByIds(ids);
  }

  public List<Long> readSaleGoodsIds(User user, Long offset, Long limit, SortType sort) {
    List<Goods> goodsList = goodsRepository.findByUserId(user, offset, limit, sort);
    return goodsList.stream().map(Goods::id).toList();
  }
}
