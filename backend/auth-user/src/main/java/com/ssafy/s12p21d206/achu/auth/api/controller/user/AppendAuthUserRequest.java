package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.ValidationUtils;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiErrorType;
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
    ValidationUtils.validateNotNull(username, AuthCoreApiErrorType.INVALID_USERNAME_FORMAT);
    ValidationUtils.validateUsername(username);

    ValidationUtils.validateNotNull(nickname, AuthCoreApiErrorType.INVALID_NICKNAME_FORMAT);
    ValidationUtils.validateNickname(nickname);

    ValidationUtils.validateNotNull(password, AuthCoreApiErrorType.INVALID_PASSWORD_FORMAT);
    ValidationUtils.validatePassword(password);

    ValidationUtils.validateNotNull(phoneNumber, AuthCoreApiErrorType.INVALID_PHONE_NUMBER_FORMAT);
    ValidationUtils.validatePhoneNumber(phoneNumber);
    return new NewAuthUser(username, password, nickname, new Phone(phoneNumber));
  }
}
