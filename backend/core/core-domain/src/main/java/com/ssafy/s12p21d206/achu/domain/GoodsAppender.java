package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsAppender {
  private final GoodsRepository goodsRepository;

  public GoodsAppender(GoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public GoodsDetail append(User user, NewGoods newGoods) {
    // TODO: 검증 -> 카테고리 존재여부, 아기 존재여부, 해당 아기가 User의 아기인지 검증
    return goodsRepository.save(user, newGoods);
  }
}
