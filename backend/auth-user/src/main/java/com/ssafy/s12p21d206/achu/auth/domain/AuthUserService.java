package com.ssafy.s12p21d206.achu.auth.domain;

import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

  private final AuthUserValidator authUserValidator;

  public AuthUserService(AuthUserValidator authUserValidator) {
    this.authUserValidator = authUserValidator;
  }

  public boolean isNicknameUnique(String nickname) {
    return authUserValidator.isNicknameUnique(nickname);
  }
}
