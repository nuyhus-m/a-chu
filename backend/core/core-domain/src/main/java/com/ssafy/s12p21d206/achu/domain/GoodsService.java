package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

  private final GoodsReader goodsReader;
  private final GoodsAppender goodsAppender;
  private final GoodsModifier goodsModifier;
  private final GoodsDeleter goodsDeleter;
  private final GoodsSearch goodsSearch;
  private final PreferenceScoreAdder preferenceScoreAdder;
  private final BabyValidator babyValidator;

  public GoodsService(
      GoodsReader goodsReader,
      GoodsAppender goodsAppender,
      GoodsModifier goodsModifier,
      GoodsDeleter goodsDeleter,
      GoodsSearch goodsSearch,
      PreferenceScoreAdder preferenceScoreAdder,
      BabyValidator babyValidator) {
    this.goodsReader = goodsReader;
    this.goodsAppender = goodsAppender;
    this.goodsModifier = goodsModifier;
    this.goodsDeleter = goodsDeleter;
    this.goodsSearch = goodsSearch;
    this.preferenceScoreAdder = preferenceScoreAdder;
    this.babyValidator = babyValidator;
  }

  public GoodsDetail append(
      User user, NewGoods newGoods, ImageUrlsWithThumbnail imageUrlsWithThumbnail) {
    return goodsAppender.append(user, newGoods, imageUrlsWithThumbnail);
  }

  public GoodsDetail modify(User user, Long goodsId, ModifyGoods modifyGoods) {
    return goodsModifier.modify(user, goodsId, modifyGoods);
  }

  public Long delete(User user, Long goodsId) {
    return goodsDeleter.delete(user, goodsId);
  }

  public List<Goods> findGoods(User user, Long offset, Long limit, SortType sort) {
    return goodsReader.readGoods(user, offset, limit, sort);
  }

  public List<Goods> findCategoryGoods(
      User user, Long categoryId, Long offset, Long limit, SortType sort) {
    return goodsReader.readCategoryGoods(user, categoryId, offset, limit, sort);
  }

  public GoodsDetail findGoodsDetail(User user, Long babyId, Long goodsId) {
    babyValidator.validateParent(user, babyId);
    GoodsDetail goodsDetail = goodsReader.readGoodsDetail(goodsId);
    preferenceScoreAdder.addScore(babyId, goodsId, 1L);
    return goodsDetail;
  }

  public List<Goods> searchGoods(
      User user, String keyword, Long offset, Long limit, SortType sort) {
    return goodsSearch.searchGoods(user, keyword, offset, limit, sort);
  }

  public List<Goods> searchCategoryGoods(
      User user, Long categoryId, String keyword, Long offset, Long limit, SortType sort) {
    return goodsSearch.searchCategoryGoods(user, categoryId, keyword, offset, limit, sort);
  }

  public Goods modifyImages(
      User user, Long goodsId, ImageUrlsWithThumbnail imageUrlsWithThumbnail) {
    return goodsModifier.modifyImages(user, goodsId, imageUrlsWithThumbnail);
  }

  public List<Goods> recommendGoods(User user, Long babyId) {
    return goodsReader.readRecommendGoods(user, babyId);
  }
}
