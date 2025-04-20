package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsDeleter {
  private final GoodsRepository goodsRepository;
  private final GoodsValidator goodsValidator;

  public GoodsDeleter(GoodsRepository goodsRepository, GoodsValidator goodsValidator) {
    this.goodsRepository = goodsRepository;
    this.goodsValidator = goodsValidator;
  }

  public Long delete(User user, Long goodsId) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(goodsId, user.id());
    goodsValidator.validateIsSelling(goodsId);
    return goodsRepository.delete(goodsId);
  }
}
