package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.DefaultDateTime;
import java.util.Objects;

public record Goods(
    Long id,
    String title,
    String description,
    ImageUrlsWithThumbnail imageUrlsWithThumbnail,
    TradeStatus tradeStatus,
    Long price,
    DefaultDateTime defaultDateTime,
    Long categoryId,
    User user,
    Long babyId) {

  public boolean isSamePrice(Long price) {
    return Objects.equals(this.price, price);
  }
}
