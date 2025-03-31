package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.api.validation.GoodsDescription;
import com.ssafy.s12p21d206.achu.api.validation.GoodsTitle;
import com.ssafy.s12p21d206.achu.domain.ModifyGoods;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ModifyGoodsRequest(
    @NotNull @GoodsTitle String title,
    @NotNull @GoodsDescription String description,
    @NotNull @Min(0) Long price,
    @NotNull Long categoryId,
    @NotNull Long babyId) {
  public static ModifyGoods toModifyGoods(ModifyGoodsRequest request) {
    return new ModifyGoods(
        request.title(),
        request.description(),
        request.price(),
        request.categoryId(),
        request.babyId());
  }
}
