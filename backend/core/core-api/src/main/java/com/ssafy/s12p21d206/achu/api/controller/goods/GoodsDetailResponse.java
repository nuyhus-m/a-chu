package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;
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
    UserResponse seller) {}
