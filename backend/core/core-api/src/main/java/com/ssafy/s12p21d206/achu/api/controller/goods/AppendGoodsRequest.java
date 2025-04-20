package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.api.validation.GoodsDescription;
import com.ssafy.s12p21d206.achu.api.validation.GoodsTitle;
import com.ssafy.s12p21d206.achu.domain.NewGoods;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AppendGoodsRequest(
    @NotNull @GoodsTitle String title,
    @NotNull @GoodsDescription String description,
    @NotNull @Min(0) Long price,
    @NotNull Long categoryId,
    @NotNull Long babyId) {
  public NewGoods toNewGoods() {
    return new NewGoods(
        this.title(), this.description(), this.price(), this.categoryId(), this.babyId());
  }
}
