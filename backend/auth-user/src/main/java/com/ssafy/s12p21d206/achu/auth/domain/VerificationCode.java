package com.ssafy.s12p21d206.achu.auth.domain;

import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class VerificationCode {
  private final UUID id;
  private final Phone phone;
  private final String code;
  private final VerificationPurpose purpose;
  private final Duration expiresIn;
  private final AuthDefaultDateTime defaultDateTime;
  boolean isVerified;

  public VerificationCode(
      UUID id,
      Phone phone,
      String code,
      VerificationPurpose purpose,
      Duration expiresIn,
      AuthDefaultDateTime defaultDateTime,
      boolean isVerified) {
    this.id = id;
    this.phone = phone;
    this.code = code;
    this.purpose = purpose;
    this.expiresIn = expiresIn;
    this.defaultDateTime = defaultDateTime;
    this.isVerified = isVerified;
  }

  public void verify(String code) {
    if (isVerified) {
      throw new AuthCoreException(AuthCoreErrorType.INVALID_VERIFICATION_CODE);
    }

    if (!this.code.equals(code)) {
      throw new AuthCoreException(AuthCoreErrorType.INVALID_VERIFICATION_CODE);
    }

    if (defaultDateTime.createdAt().plus(expiresIn).isBefore(LocalDateTime.now())) {
      throw new AuthCoreException(AuthCoreErrorType.INVALID_VERIFICATION_CODE);
    }

    isVerified = true;
  }

  public UUID getId() {
    return id;
  }

  public Phone getPhone() {
    return phone;
  }

  public String getCode() {
    return code;
  }

  public VerificationPurpose getPurpose() {
    return purpose;
  }

  public Duration getExpiresIn() {
    return expiresIn;
  }

  public AuthDefaultDateTime getDefaultDateTime() {
    return defaultDateTime;
  }

  public boolean isVerified() {
    return isVerified;
  }
}
