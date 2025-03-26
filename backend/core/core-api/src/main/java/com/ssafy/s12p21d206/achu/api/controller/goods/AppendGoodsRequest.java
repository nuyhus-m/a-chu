package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.NewGoods;
import java.util.List;

public record AppendGoodsRequest(
    String title, String description, Long price, Long categoryId, Long babyId) {
  public NewGoods toNewGoods(List<String> imgUrls) {
    return new NewGoods(
        this.title(), this.description(), imgUrls, this.price(), this.categoryId(), this.babyId());
  }
}
