package com.ssafy.s12p21d206.achu.recommend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record RecommendationResponse(List<Long> recommendGoodsIds) {
  // Jackson을 위한 명시적 생성자
  @JsonCreator
  public RecommendationResponse(@JsonProperty("recommended_items") List<Long> recommendGoodsIds) {
    this.recommendGoodsIds = recommendGoodsIds != null ? recommendGoodsIds : List.of();
  }
}
