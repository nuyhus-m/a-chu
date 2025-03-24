package com.ssafy.s12p21d206.achu.auth.domain;

import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthUserAppender {

  private final AuthUserRepository authUserRepository;
  private final VerificationCodeVerifier verificationCodeVerifier;

  private final PasswordEncoder passwordEncoder;

  public AuthUserAppender(
      AuthUserRepository authUserRepository,
      VerificationCodeVerifier verificationCodeVerifier,
      PasswordEncoder passwordEncoder) {
    this.authUserRepository = authUserRepository;
    this.verificationCodeVerifier = verificationCodeVerifier;
    this.passwordEncoder = passwordEncoder;
  }

  public AuthUser append(NewAuthUser newAuthUser, UUID verificationCodeId) {
    verificationCodeVerifier.validateVerificationCodeVerified(
        newAuthUser.phone(), verificationCodeId, VerificationPurpose.SIGN_UP);
    newAuthUser.encodePassword(passwordEncoder);
    return authUserRepository.save(newAuthUser);
  }
}
