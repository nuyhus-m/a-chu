package com.ssafy.s12p21d206.achu.domain;

public record NewGoods(
    String title, String description, Long price, Long categoryId, Long babyId) {}
