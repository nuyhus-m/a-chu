package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.ChatStatus;
import com.ssafy.s12p21d206.achu.domain.Goods;
import com.ssafy.s12p21d206.achu.domain.LikeStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record GoodsResponse(
    Long id,
    String title,
    String imgUrl,
    Long price,
    LocalDateTime createdAt,
    Long chatCount,
    int likedUsersCount,
    Boolean likedByUser) {

  public static List<com.ssafy.s12p21d206.achu.api.controller.goods.GoodsResponse> of(
      List<Goods> goodsList, List<ChatStatus> chatStatuses, Map<Long, LikeStatus> likeStatusMap) {
    Map<Long, ChatStatus> chatStatusMap = chatStatuses.stream()
        .collect(Collectors.toMap(ChatStatus::getGoodsId, Function.identity()));

    return goodsList.stream()
        .map(goods -> {
          ChatStatus chatStatus = chatStatusMap.get(goods.getId());
          LikeStatus likeStatus = likeStatusMap.get(goods.getId());

          return new GoodsResponse(
              goods.id(),
              goods.title(),
              goods.imgUrl(),
              goods.price(),
              goods.getCreatedAt(),
              chatStatus.getChatCount(),
              likeStatus.count(),
              likeStatus.isLike());
        })
        .toList();
  }
}
