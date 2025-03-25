package com.ssafy.s12p21d206.achu.auth.api.controller.validation;

public class VerificationCodeValidator extends AuthRegexValidator<VerificationCode> {

  private static final String REGEX = "^\\d{6}$";

  @Override
  protected String getRegex(VerificationCode constraintAnnotation) {
    return REGEX;
  }
}
