package com.ssafy.s12p21d206.achu.auth.api.controller.validation;

public class UsernameValidator extends AuthRegexValidator<Username> {
  private static final String REGEX = "^[a-zA-Z0-9]{4,16}$";

  @Override
  protected String getRegex(Username constraintAnnotation) {
    return REGEX;
  }
}
