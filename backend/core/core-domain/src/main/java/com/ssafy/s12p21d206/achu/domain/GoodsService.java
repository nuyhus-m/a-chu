package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

  private final GoodsReader goodsReader;
  private final GoodsAppender goodsAppender;
  private final GoodsModifier goodsModifier;
  private final GoodsValidator goodsValidator;

  public GoodsService(
      GoodsReader goodsReader,
      GoodsAppender goodsAppender,
      GoodsModifier goodsModifier,
      GoodsValidator goodsValidator) {
    this.goodsReader = goodsReader;
    this.goodsAppender = goodsAppender;
    this.goodsModifier = goodsModifier;
    this.goodsValidator = goodsValidator;
  }

  public Long append(User user, NewGoods newGoods) {
    GoodsDetail goodsDetail = goodsAppender.append(user, newGoods);
    return goodsDetail.id();
  }

  public GoodsDetail modifyGoods(User user, Long id, ModifyGoods modifyGoods) {
    goodsValidator.validateExists(id);
    goodsValidator.validateOwner(user.id(), id);
    goodsValidator.validateSelling(id);
    return goodsModifier.modifyGoods(id, modifyGoods);
  }

  public List<Goods> findGoods(User user, Long offset, Long limit, SortType sort) {
    return goodsReader.readGoods(user, offset, limit, sort);
  }

  public List<Goods> findCategoryGoods(
      User user, Long categoryId, Long offset, Long limit, SortType sort) {
    return goodsReader.readCategoryGoods(user, categoryId, offset, limit, sort);
  }

  public GoodsDetail findGoodsDetail(Long id) {
    return goodsReader.readGoodsDetail(id);
  }

  public CategoryId findCategoryIdByGoodsId(Long id) {
    return goodsReader.readCategoryIdByGoodsId(id);
  }

  public User findUserIdByGoodsId(Long id) {
    return goodsReader.readUserIdByGoodsId(id);
  }
}
