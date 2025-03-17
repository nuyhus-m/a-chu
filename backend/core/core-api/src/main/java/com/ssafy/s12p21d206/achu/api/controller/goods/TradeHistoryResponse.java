package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;

public record TradeHistoryResponse(
    Long id, TradeStatus tradeStatus, String title, String imgUrl, Long price) {}
