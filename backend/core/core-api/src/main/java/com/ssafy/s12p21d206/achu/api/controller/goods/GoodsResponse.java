package com.ssafy.s12p21d206.achu.api.controller.goods;

import java.time.LocalDateTime;

public record GoodsResponse(
    Long id,
    String title,
    String imgUrl,
    Long price,
    LocalDateTime createdAt,
    Long chatCount,
    Long likedUsersCount,
    Boolean likedByUser) {}
