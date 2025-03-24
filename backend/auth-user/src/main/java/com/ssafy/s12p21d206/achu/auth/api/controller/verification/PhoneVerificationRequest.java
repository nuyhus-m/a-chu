package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.api.controller.ValidationUtils;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationPurpose;

public record PhoneVerificationRequest(String phoneNumber, VerificationPurpose purpose) {

  public PhoneVerificationRequest {
    ValidationUtils.validateNotNull(phoneNumber, AuthCoreApiErrorType.NULL_PHONE_NUMBER);
    ValidationUtils.validatePhoneNumber(phoneNumber);

    ValidationUtils.validateNotNull(purpose, AuthCoreApiErrorType.NULL_VERIFICATION_PURPOSE);
  }

  public Phone toPhoneNumber() {
    return new Phone(phoneNumber);
  }

  public VerificationPurpose toVerificationPurpose() {
    return purpose;
  }
}
