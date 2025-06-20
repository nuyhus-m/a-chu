package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationCode;
import java.time.Duration;
import java.util.UUID;

public record PhoneVerificationResponse(UUID id, Duration expiresIn) {

  public static PhoneVerificationResponse of(VerificationCode phoneVerificationCode) {
    return new PhoneVerificationResponse(
        phoneVerificationCode.getId(), phoneVerificationCode.getExpiresIn());
  }
}
