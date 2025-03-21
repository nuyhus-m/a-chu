package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.domain.VerificationCode;
import java.time.Duration;
import java.util.UUID;

public record PhoneVerificationResponse(UUID verificationId, Duration expiresIn) {

  public static PhoneVerificationResponse of(VerificationCode phoneVerificationCode) {
    return new PhoneVerificationResponse(
        phoneVerificationCode.id(), phoneVerificationCode.expiresIn());
  }
}
