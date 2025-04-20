package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.NewTrade;
import jakarta.validation.constraints.NotNull;

public record AppendTradeRequest(@NotNull Long buyerId) {
  public NewTrade toNewTrade() {
    return new NewTrade(this.buyerId);
  }
}
