package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.api.controller.ValidationUtils;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiErrorType;
import java.util.UUID;

public record VerifyVerificationCodeRequest(UUID id, String code) {

  public VerifyVerificationCodeRequest {
    ValidationUtils.validateNotNull(id, AuthCoreApiErrorType.NULL_ID);

    ValidationUtils.validateNotNull(code, AuthCoreApiErrorType.NULL_VERIFICATION_CODE);
    ValidationUtils.validateVerificationCode(code);
  }
}
