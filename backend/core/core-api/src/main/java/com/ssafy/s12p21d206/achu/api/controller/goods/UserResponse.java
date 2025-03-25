package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.Seller;

public record UserResponse(Long id, String nickname, String imgUrl) {
  public static UserResponse from(Seller seller) {
    return new UserResponse(seller.id(), seller.nickname(), seller.imgUrl());
  }
}
