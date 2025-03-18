package com.ssafy.s12p21d206.achu.api.controller.goods;

public record ModifyGoodsRequest(
    String title, String description, Long price, String categoryName, Long id) {}
