package com.ssafy.s12p21d206.achu.auth.api.controller.validation;

public class PhoneNumberValidator extends AuthRegexValidator<PhoneNumber> {
  private static final String REGEX = "^(01[016789])(\\d{3,4})(\\d{4})$";

  @Override
  protected String getRegex(PhoneNumber constraintAnnotation) {
    return REGEX;
  }
}
