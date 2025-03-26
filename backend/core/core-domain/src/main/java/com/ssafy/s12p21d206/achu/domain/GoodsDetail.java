package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;
import java.time.LocalDateTime;
import java.util.List;

public record GoodsDetail(
    Long id,
    String title,
    String description,
    List<String> imgUrls,
    TradeStatus tradeStatus,
    Long price,
    LocalDateTime createdAt,
    Long categoryId,
    Long userId,
    Long babyId) {}
