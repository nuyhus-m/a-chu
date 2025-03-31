package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsModifier {
  private final GoodsRepository goodsRepository;
  private final GoodsValidator goodsValidator;
  private final BabyValidator babyValidator;

  public GoodsModifier(
      GoodsRepository goodsRepository, GoodsValidator goodsValidator, BabyValidator babyValidator) {
    this.goodsRepository = goodsRepository;
    this.goodsValidator = goodsValidator;
    this.babyValidator = babyValidator;
  }

  public GoodsDetail modify(User user, Long goodsId, ModifyGoods modifyGoods) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(user.id(), goodsId);
    goodsValidator.validateIsSelling(goodsId);
    babyValidator.validateExists(modifyGoods.babyId());
    babyValidator.validateParent(user, modifyGoods.babyId());
    return goodsRepository.modifyGoods(goodsId, modifyGoods);
  }

  public Goods modifyImages(
      User user, Long goodsId, ImageUrlsWithThumbnail imageUrlsWithThumbnail) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(user.id(), goodsId);
    goodsValidator.validateIsSelling(goodsId);
    return goodsRepository.modifyImages(goodsId, imageUrlsWithThumbnail);
  }
}
