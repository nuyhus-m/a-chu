package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.GoodsDetail;
import com.ssafy.s12p21d206.achu.domain.LikeStatus;
import com.ssafy.s12p21d206.achu.domain.TradeStatus;
import java.time.LocalDateTime;
import java.util.List;

public record GoodsDetailResponse(
    Long id,
    String title,
    String description,
    List<String> imgUrls,
    TradeStatus tradeStatus,
    Long price,
    LocalDateTime createdAt,
    Long likedUsersCount,
    Boolean likedByUser,
    CategoryResponse category,
    UserResponse seller) {
  public static GoodsDetailResponse of(
      GoodsDetail goodsDetail,
      LikeStatus likeStatus,
      CategoryResponse category,
      UserResponse seller) {
    return new GoodsDetailResponse(
        goodsDetail.goods().id(),
        goodsDetail.goods().title(),
        goodsDetail.goods().description(),
        goodsDetail.goods().imgUrls(),
        goodsDetail.goods().tradeStatus(),
        goodsDetail.goods().price(),
        goodsDetail.goods().defaultDateTime().createdAt(),
        (long) likeStatus.count(),
        likeStatus.isLike(),
        category,
        seller);
  }
}
