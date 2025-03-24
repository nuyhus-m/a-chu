package com.ssafy.s12p21d206.achu.auth.api.controller;

import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiErrorType;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiException;

public class ValidationUtils {
  private ValidationUtils() {}

  public static void validatePhoneNumber(String phoneNumber) {
    String regex = "^(01[016789])(\\d{3,4})(\\d{4})$";

    if (!phoneNumber.matches(regex)) {
      throw new AuthCoreApiException(AuthCoreApiErrorType.INVALID_PHONE_NUMBER_FORMAT);
    }
  }

  public static void validateVerificationCode(String code) {
    String regex = "^\\d{6}$";

    if (!code.matches(regex)) {
      throw new AuthCoreApiException(AuthCoreApiErrorType.INVALID_VERIFICATION_CODE_FORMAT);
    }
  }

  public static <T> void validateNotNull(T value, AuthCoreApiErrorType errorType) {
    if (value == null) {
      throw new AuthCoreApiException(errorType);
    }
  }
}
