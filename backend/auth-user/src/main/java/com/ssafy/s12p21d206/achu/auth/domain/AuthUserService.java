package com.ssafy.s12p21d206.achu.auth.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

  private final AuthUserValidator authUserValidator;
  private final AuthUserAppender authUserAppender;
  private final AuthUserModifier authUserModifier;

  public AuthUserService(
      AuthUserValidator authUserValidator,
      AuthUserAppender authUserAppender,
      AuthUserModifier authUserModifier) {
    this.authUserValidator = authUserValidator;
    this.authUserAppender = authUserAppender;
    this.authUserModifier = authUserModifier;
  }

  public boolean isNicknameUnique(String nickname) {
    return authUserValidator.isNicknameUnique(nickname);
  }

  public boolean isUsernameUnique(String username) {
    return authUserValidator.isUsernameUnique(username);
  }

  public AuthUser appendAuthUser(NewAuthUser newAuthUser, UUID verificationCodeId) {
    return authUserAppender.append(newAuthUser, verificationCodeId);
  }

  public AuthUser modifyNickname(Long userId, String nickname) {
    return authUserModifier.modifyNickname(userId, nickname);
  }
}
