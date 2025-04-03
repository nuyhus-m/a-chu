package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsModifier {
  private final GoodsRepository goodsRepository;
  private final GoodsValidator goodsValidator;
  private final BabyValidator babyValidator;
  private final GoodsReader goodsReader;

  public GoodsModifier(
      GoodsRepository goodsRepository,
      GoodsValidator goodsValidator,
      BabyValidator babyValidator,
      GoodsReader goodsReader) {
    this.goodsRepository = goodsRepository;
    this.goodsValidator = goodsValidator;
    this.babyValidator = babyValidator;
    this.goodsReader = goodsReader;
  }

  public GoodsDetail modify(User user, Long goodsId, ModifyGoods modifyGoods) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(user.id(), goodsId);
    goodsValidator.validateIsSelling(goodsId);
    Goods goods = goodsReader.getGoods(goodsId);
    babyValidator.validateExists(goods.babyId());
    babyValidator.validateParent(user, goods.babyId());
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
