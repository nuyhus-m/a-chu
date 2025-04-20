package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.ChatRoomCountStatus;
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
    Long likedUsersCount,
    Boolean likedByUser) {

  public static List<com.ssafy.s12p21d206.achu.api.controller.goods.GoodsResponse> of(
      List<Goods> goodsList,
      List<ChatRoomCountStatus> chatRoomCountStatuses,
      Map<Long, LikeStatus> likeStatusMap) {
    Map<Long, ChatRoomCountStatus> chatStatusMap = chatRoomCountStatuses.stream()
        .collect(Collectors.toMap(ChatRoomCountStatus::goodsId, Function.identity()));

    return goodsList.stream()
        .map(goods -> {
          ChatRoomCountStatus chatRoomCountStatus =
              chatStatusMap.getOrDefault(goods.id(), new ChatRoomCountStatus(0L, 0L));
          LikeStatus likeStatus = likeStatusMap.getOrDefault(goods.id(), new LikeStatus(0, false));

          return new GoodsResponse(
              goods.id(),
              goods.title(),
              goods.imageUrlsWithThumbnail().thumbnailImageUrl(),
              goods.price(),
              goods.defaultDateTime().createdAt(),
              chatRoomCountStatus.chatCount(),
              likeStatus.count(),
              likeStatus.isLike());
        })
        .toList();
  }
}
