package com.ssafy.s12p21d206.achu.builder;

import com.ssafy.s12p21d206.achu.auth.domain.Phone;
import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationCode;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationPurpose;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class VerificationCodeBuilder {
  private VerificationCodeBuilder() {}

  public static VerificationCode createVerified(
      VerificationPurpose purpose, LocalDateTime createdAt) {
    String code = "123456";
    Duration expiresIn = Duration.ofMinutes(3);

    return new VerificationCode(
        UUID.randomUUID(),
        new Phone("01012341234"),
        code,
        purpose,
        expiresIn,
        new AuthDefaultDateTime(createdAt, createdAt),
        true);
  }

  public static VerificationCode createNonVerified(
      VerificationPurpose purpose, LocalDateTime createdAt) {
    String code = "123456";
    Duration expiresIn = Duration.ofMinutes(3);

    return new VerificationCode(
        UUID.randomUUID(),
        new Phone("01012341234"),
        code,
        purpose,
        expiresIn,
        new AuthDefaultDateTime(createdAt, createdAt),
        false);
  }
}
