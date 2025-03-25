package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.validation.Nickname;
import com.ssafy.s12p21d206.achu.auth.api.controller.validation.Password;
import com.ssafy.s12p21d206.achu.auth.api.controller.validation.PhoneNumber;
import com.ssafy.s12p21d206.achu.auth.api.controller.validation.Username;
import com.ssafy.s12p21d206.achu.auth.domain.user.NewAuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AppendAuthUserRequest(
    @NotNull @Username String username,
    @NotNull @Password String password,
    @NotNull @Nickname String nickname,
    @NotNull @PhoneNumber String phoneNumber,
    @NotNull UUID verificationCodeId) {

  public NewAuthUser toNewAuthUser() {
    return new NewAuthUser(username, password, nickname, new Phone(phoneNumber));
  }
}
