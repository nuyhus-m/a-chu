package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

  public List<LikeStatus> findLikeStatus(User user, List<Long> goodsIds) {
    /*
     * 각 goodsId에 대해 아래 정보들을 조회해야 합니다:
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
