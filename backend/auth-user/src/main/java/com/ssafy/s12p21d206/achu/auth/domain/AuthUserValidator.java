package com.ssafy.s12p21d206.achu.auth.domain;

import org.springframework.stereotype.Component;

@Component
public class AuthUserValidator {

  private final AuthUserRepository authUserRepository;

  public AuthUserValidator(AuthUserRepository authUserRepository) {
    this.authUserRepository = authUserRepository;
  }

  public boolean isNicknameUnique(String nickname) {
    return !authUserRepository.existsByNickname(nickname);
  }

  public boolean isUsernameUnique(String username) {
    return !authUserRepository.existsByUsername(username);
  }
}
