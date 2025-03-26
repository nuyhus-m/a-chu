package com.ssafy.s12p21d206.achu.auth.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthUserModifier {

  private final AuthUserReader authUserReader;
  private final AuthUserValidator authUserValidator;

  private final AuthUserRepository authUserRepository;

  private final PasswordEncoder passwordEncoder;

  public AuthUserModifier(
      AuthUserReader authUserReader,
      AuthUserValidator authUserValidator,
      AuthUserRepository authUserRepository,
      PasswordEncoder passwordEncoder) {
    this.authUserReader = authUserReader;
    this.authUserValidator = authUserValidator;
    this.authUserRepository = authUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public AuthUser modifyNickname(Long userId, String nickname) {
    authUserValidator.validateNicknameUnique(nickname);
    return authUserRepository.modifyNickname(userId, nickname);
  }

  public AuthUser modifyPassword(Long userId, String oldPassword, String newPassword) {
    AuthUser authUser = authUserReader.findAuthUser(userId);
    authUser.validatePassword(passwordEncoder, oldPassword);

    String encodedPassword = passwordEncoder.encode(oldPassword);
    return authUserRepository.modifyPassword(userId, encodedPassword);
  }
}
