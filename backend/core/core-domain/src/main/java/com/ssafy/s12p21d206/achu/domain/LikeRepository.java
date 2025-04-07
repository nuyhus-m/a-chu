package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LikeRepository {
  int count(Long goodsId);

  boolean isLike(User user, Long goodsId);

  Map<Long, LikeStatus> status(User user, List<Long> goodsIds);

  void like(User user, Long goodsId);

  void deleteLike(User user, Long goodsId);

  List<Long> findLikedGoodsIds(User user, Long offset, Long limit, SortType sort);

  Set<Long> findLikerIds(Long goodsId);
}
