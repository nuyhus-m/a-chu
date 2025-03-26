package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.api.controller.validation.PhoneNumber;
import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationPurpose;
import jakarta.validation.constraints.NotNull;

public record PhoneVerificationRequest(
    @NotNull @PhoneNumber String phoneNumber, @NotNull VerificationPurpose purpose) {

  public Phone toPhoneNumber() {
    return new Phone(phoneNumber);
  }

  public VerificationPurpose toVerificationPurpose() {
    return purpose;
  }
}
