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
  private final GoodsDeleter goodsDeleter;
  private final GoodsSearch goodsSearch;

  public GoodsService(
      GoodsReader goodsReader,
      GoodsAppender goodsAppender,
      GoodsModifier goodsModifier,
      GoodsValidator goodsValidator,
      GoodsDeleter goodsDeleter,
      GoodsSearch goodsSearch) {
    this.goodsReader = goodsReader;
    this.goodsAppender = goodsAppender;
    this.goodsModifier = goodsModifier;
    this.goodsValidator = goodsValidator;
    this.goodsDeleter = goodsDeleter;
    this.goodsSearch = goodsSearch;
  }

  public Long append(User user, NewGoods newGoods) {
    GoodsDetail goodsDetail = goodsAppender.append(user, newGoods);
    return goodsDetail.id();
  }

  public GoodsDetail modify(User user, Long id, ModifyGoods modifyGoods) {
    goodsValidator.validateExists(id);
    goodsValidator.validateOwner(user.id(), id);
    goodsValidator.validateSelling(id);
    return goodsModifier.modify(id, modifyGoods);
  }

  public Long delete(User user, Long id) {
    goodsValidator.validateExists(id);
    goodsValidator.validateOwner(user.id(), id);
    return goodsDeleter.delete(id);
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

  public List<Goods> searchGoods(
      User user, String keyword, Long offset, Long limit, SortType sort) {
    return goodsSearch.searchGoods(user, keyword, offset, limit, sort);
  }

  public List<Goods> searchCategoryGoods(
      User user, Long categoryId, String keyword, Long offset, Long limit, SortType sort) {
    return goodsSearch.searchCategoryGoods(user, categoryId, keyword, offset, limit, sort);
  }
}
