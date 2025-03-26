package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsAppender {
  private final GoodsRepository goodsRepository;

  public GoodsAppender(GoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public GoodsDetail append(User user, NewGoods newGoods) {
    return goodsRepository.save(user, newGoods);
  }
}
