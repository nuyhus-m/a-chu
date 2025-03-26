package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsDeleter {
  private final GoodsRepository goodsRepository;

  public GoodsDeleter(GoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public Long delete(Long id) {
    return goodsRepository.delete(id);
  }
}
