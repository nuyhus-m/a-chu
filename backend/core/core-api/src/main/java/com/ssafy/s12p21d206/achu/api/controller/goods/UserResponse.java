package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.UserDetail;

public record UserResponse(Long id, String nickname, String imgUrl) {
  public static UserResponse from(UserDetail userDetail) {
    return new UserResponse(userDetail.id(), userDetail.nickname(), userDetail.imgUrl());
  }
}
