package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class LikeReader {

  private LikeRepository likeRepository;

  public LikeReader(LikeRepository likeRepository) {
    this.likeRepository = likeRepository;
  }

  public int count(Long goodsId) {
    return likeRepository.count(goodsId);
  }

  public boolean isLike(User user, Long goodsId) {
    return likeRepository.isLike(user, goodsId);
  }

  public Map<Long, LikeStatus> status(User user, List<Long> goodIds) {
    return likeRepository.status(user, goodIds);
  }

  public List<Long> readLikedGoods(User user, Long offset, Long limit, SortType sort) {
    return likeRepository.findLikedGoodsIds(user, offset, limit, sort);
  }

  public Set<Long> readLikerIds(Long goodsId) {
    return likeRepository.findLikerIds(goodsId);
  }
}
