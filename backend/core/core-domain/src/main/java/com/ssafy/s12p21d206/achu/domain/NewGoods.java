package com.ssafy.s12p21d206.achu.domain;

import java.util.List;

public record NewGoods(
    String title,
    String description,
    List<String> imgUrls,
    Long price,
    Long categoryId,
    Long babyId) {}
