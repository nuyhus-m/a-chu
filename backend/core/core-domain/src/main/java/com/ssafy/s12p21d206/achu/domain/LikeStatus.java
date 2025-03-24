package com.ssafy.s12p21d206.achu.domain;

public record LikeStatus(Long goodsId, Long likedUsersCount, Boolean likedByUser) {

  public Long getGoodsId() {
    return goodsId;
  }

  public Long getLikedUsersCount() {
    return likedUsersCount;
  }

  public Boolean getLikedByUser() {
    return likedByUser;
  }
}
