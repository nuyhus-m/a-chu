package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

<<<<<<< HEAD
  private final LikeReader likeReader;
  private final LikeProcessor likeProcessor;

  public LikeService(LikeReader likeReader, LikeProcessor likeProcessor) {
    this.likeReader = likeReader;
    this.likeProcessor = likeProcessor;
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

  public void like(User user, Long goodsId) {
    likeProcessor.like(user, goodsId);
  }

  public void deleteLike(User user, Long goodsId) {
    likeProcessor.deleteLike(user, goodsId);
  }

  public LikeStatus findLikeStatus(User user, Long goodsId) {
    /*
     * 해당하는 goodsId에 대해 아래 정보들을 조회해야 합니다:
     *
     * 1. 전체 좋아요 수
     *    - likeRepository.countByGoodsId(goodsId)
     *
     * 2. 해당 사용자가 좋아요를 눌렀는지 여부
     *    - likeRepository.existsByGoodsIdAndUserId(goodsId, user.id())
     *
     * 결과는 goodsId를 기준으로 LikeStatus 객체로 만들어 반환해야 합니다:
     *   new LikeStatus(goodsId, likedUsersCount, likedByUser)
     */
    throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
  }
}
