package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

  private final LikeReader likeReader;

  public LikeService(LikeReader likeReader) {
    this.likeReader = likeReader;
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
}
