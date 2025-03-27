package com.ssafy.s12p21d206.achu.auth.domain.user;

import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

  private final AuthUserValidator authUserValidator;
  private final AuthUserAppender authUserAppender;
  private final AuthUserModifier authUserModifier;
  private final AuthUserReader authUserReader;

  public AuthUserService(
      AuthUserValidator authUserValidator,
      AuthUserAppender authUserAppender,
      AuthUserModifier authUserModifier,
      AuthUserReader authUserReader) {
    this.authUserValidator = authUserValidator;
    this.authUserAppender = authUserAppender;
    this.authUserModifier = authUserModifier;
    this.authUserReader = authUserReader;
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

  public AuthUser modifyPassword(Long userId, String oldPassword, String newPassword) {
    return authUserModifier.modifyPassword(userId, oldPassword, newPassword);
  }

  public AuthUser modifyPhoneNumber(Long userId, Phone phone, UUID verificationCodeId) {
    return authUserModifier.modifyPhoneNumber(userId, phone, verificationCodeId);
  }

  public AuthUser resetPassword(String username, String newPassword, UUID verificationCodeId) {
    return authUserModifier.resetPassword(username, newPassword, verificationCodeId);
  }

  public AuthUser findUser(Long userId) {
    return authUserReader.findAuthUser(userId);
  }
}
