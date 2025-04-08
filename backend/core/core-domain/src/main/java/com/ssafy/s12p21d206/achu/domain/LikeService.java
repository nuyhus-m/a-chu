package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

  private final LikeReader likeReader;
  private final LikeProcessor likeProcessor;
  private final GoodsReader goodsReader;
  private final PreferenceScoreAdder preferenceScoreAdder;

  public LikeService(
      LikeReader likeReader,
      LikeProcessor likeProcessor,
      GoodsReader goodsReader,
      PreferenceScoreAdder preferenceScoreAdder) {
    this.likeReader = likeReader;
    this.likeProcessor = likeProcessor;
    this.goodsReader = goodsReader;
    this.preferenceScoreAdder = preferenceScoreAdder;
  }

  public LikeStatus status(User user, Long goodsId) {
    int count = likeReader.count(goodsId);
    boolean isLike = likeReader.isLike(user, goodsId);

    return new LikeStatus(count, isLike);
  }

  /**
   * @return  goodsId를 key로 하고 LikeStatus가 value인 Map을 반환합니다.
   */
  public Map<Long, LikeStatus> status(User user, List<Long> goodsId) {
    return likeReader.status(user, goodsId);
  }

  public void like(User user, Long babyId, Long goodsId) {
    likeProcessor.like(user, goodsId);
    preferenceScoreAdder.addScore(babyId, goodsId, 3L);
  }

  public void deleteLike(User user, Long goodsId) {
    likeProcessor.deleteLike(user, goodsId);
  }

  public List<Goods> findLikedGoods(User user, Long offset, Long limit, SortType sort) {
    List<Long> goodsIds = likeReader.readLikedGoods(user, offset, limit, sort);
    return goodsReader.readGoodsByIds(goodsIds);
  }
}
