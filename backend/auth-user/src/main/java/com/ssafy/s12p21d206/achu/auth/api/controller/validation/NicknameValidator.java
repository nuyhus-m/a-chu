package com.ssafy.s12p21d206.achu.auth.api.controller.validation;

public class NicknameValidator extends AuthRegexValidator<Nickname> {
  private static final String REGEX = "^[a-zA-Z0-9가-힣]{2,6}$";

  @Override
  protected String getRegex(Nickname constraintAnnotation) {
    return REGEX;
  }
}
