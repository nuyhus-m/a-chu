package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsAppender {
  private final GoodsRepository goodsRepository;
  private final CategoryValidator categoryValidator;
  private final BabyValidator babyValidator;

  public GoodsAppender(
      GoodsRepository goodsRepository,
      CategoryValidator categoryValidator,
      BabyValidator babyValidator) {
    this.goodsRepository = goodsRepository;
    this.categoryValidator = categoryValidator;
    this.babyValidator = babyValidator;
  }

  public GoodsDetail append(
      User user, NewGoods newGoods, ImageUrlsWithThumbnail imageUrlsWithThumbnail) {
    babyValidator.validateExists(newGoods.babyId());
    babyValidator.validateParent(user, newGoods.babyId());
    categoryValidator.validateExists(newGoods.categoryId());
    return goodsRepository.save(user, newGoods, imageUrlsWithThumbnail);
  }
}
