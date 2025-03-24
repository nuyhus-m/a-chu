package com.ssafy.s12p21d206.achu.auth.domain;

import org.springframework.stereotype.Component;

@Component
public class AuthUserModifier {

  private final AuthUserRepository authUserRepository;
  private final AuthUserValidator authUserValidator;

  public AuthUserModifier(
      AuthUserRepository authUserRepository, AuthUserValidator authUserValidator) {
    this.authUserRepository = authUserRepository;
    this.authUserValidator = authUserValidator;
  }

  public AuthUser modifyNickname(Long userId, String nickname) {
    authUserValidator.validateNicknameUnique(nickname);
    return authUserRepository.modifyNickname(userId, nickname);
  }
}
