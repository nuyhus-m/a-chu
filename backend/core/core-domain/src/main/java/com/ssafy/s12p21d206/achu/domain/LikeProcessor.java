package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class LikeProcessor {
  private final LikeRepository likeRepository;
  private final GoodsValidator goodsValidator;

  public LikeProcessor(LikeRepository likeRepository, GoodsValidator goodsValidator) {
    this.likeRepository = likeRepository;
    this.goodsValidator = goodsValidator;
  }

  public void like(User user, Long goodsId) {
    goodsValidator.validateExists(goodsId);
    likeRepository.like(user, goodsId);
  }

  public void deleteLike(User user, Long goodsId) {
    goodsValidator.validateExists(goodsId);
    likeRepository.deleteLike(user, goodsId);
  }
}
