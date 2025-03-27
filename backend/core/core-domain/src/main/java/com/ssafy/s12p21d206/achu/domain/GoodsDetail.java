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
    Long userId, // TODO: User 객체로 변환 그리고 DB에서 가져올 떄 new User(userId)로 생성해서 생성자로 넣기
    Long babyId) {}
