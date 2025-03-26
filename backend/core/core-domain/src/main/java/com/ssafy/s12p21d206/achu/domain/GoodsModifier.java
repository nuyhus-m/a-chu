package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsModifier {
  private final GoodsRepository goodsRepository;

  public GoodsModifier(GoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public GoodsDetail modifyGoods(Long id, ModifyGoods modifyGoods) {
    return goodsRepository.modifyGoods(id, modifyGoods);
  }
}
