package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUser;

public record AuthUserResponse(
    Long id, String username, String nickname, String phoneNumber, String profileImageUrl) {

  public static AuthUserResponse from(AuthUser authUser) {
    return new AuthUserResponse(
        authUser.id(),
        authUser.username(),
        authUser.nickname(),
        authUser.phone().number(),
        authUser.profileImageUrl());
  }
}
