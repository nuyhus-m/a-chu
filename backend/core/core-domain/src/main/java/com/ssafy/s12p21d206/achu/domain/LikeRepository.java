package com.ssafy.s12p21d206.achu.domain;

public interface LikeRepository {
  Long countByGoodsId(Long goodsId);

  Boolean existsByGoodsIdAndUserId(Long goodsId, Long userId);
}
