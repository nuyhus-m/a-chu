package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class GoodsModifier {
  private final GoodsRepository goodsRepository;
  private final GoodsValidator goodsValidator;
  private final BabyValidator babyValidator;
  private final GoodsReader goodsReader;
  private final GoodsEventPublisher goodsEventPublisher;

  public GoodsModifier(
      GoodsRepository goodsRepository,
      GoodsValidator goodsValidator,
      BabyValidator babyValidator,
      GoodsReader goodsReader,
      GoodsEventPublisher goodsEventPublisher) {
    this.goodsRepository = goodsRepository;
    this.goodsValidator = goodsValidator;
    this.babyValidator = babyValidator;
    this.goodsReader = goodsReader;
    this.goodsEventPublisher = goodsEventPublisher;
  }

  public GoodsDetail modify(User user, Long goodsId, ModifyGoods modifyGoods) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(goodsId, user.id());
    goodsValidator.validateIsSelling(goodsId);
    Goods goods = goodsReader.getGoods(goodsId);
    babyValidator.validateExists(goods.babyId());
    babyValidator.validateParent(user, goods.babyId());
    GoodsDetail modifyGoodsDetail = goodsRepository.modifyGoods(goodsId, modifyGoods);
    goodsEventPublisher.publishPriceChangeEvent(goods, modifyGoods);

    return modifyGoodsDetail;
  }

  public Goods modifyImages(
      User user, Long goodsId, ImageUrlsWithThumbnail imageUrlsWithThumbnail) {
    goodsValidator.validateExists(goodsId);
    goodsValidator.validateOwner(goodsId, user.id());
    goodsValidator.validateIsSelling(goodsId);
    return goodsRepository.modifyImages(goodsId, imageUrlsWithThumbnail);
  }
}
