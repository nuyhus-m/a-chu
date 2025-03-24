package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.domain.NewAuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.Phone;
import java.util.UUID;

public record AppendAuthUserRequest(
    String username,
    String password,
    String nickname,
    String phoneNumber,
    UUID verificationCodeId) {

  public NewAuthUser toNewAuthUser() {
    return new NewAuthUser(username, password, nickname, new Phone(phoneNumber));
  }
}
