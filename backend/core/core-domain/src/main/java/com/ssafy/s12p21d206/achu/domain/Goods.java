package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;
import java.util.List;

public record Goods(
    Long id,
    String title,
    String description,
    List<String> imgUrls,
    TradeStatus tradeStatus,
    Long price,
    DefaultDateTime defaultDateTime,
    Long categoryId,
    User user,
    Long babyId) {}
