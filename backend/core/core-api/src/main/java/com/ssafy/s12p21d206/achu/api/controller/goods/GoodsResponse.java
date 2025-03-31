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
          ChatStatus chatStatus = chatStatusMap.getOrDefault(goods.id(), new ChatStatus(0L, 0L));
          LikeStatus likeStatus = likeStatusMap.getOrDefault(goods.id(), new LikeStatus(0, false));

          return new GoodsResponse(
              goods.id(),
              goods.title(),
              goods.imgUrls().get(0),
              goods.price(),
              goods.defaultDateTime().createdAt(),
              chatStatus.getChatCount(),
              likeStatus.count(),
              likeStatus.isLike());
        })
        .toList();
  }
}
