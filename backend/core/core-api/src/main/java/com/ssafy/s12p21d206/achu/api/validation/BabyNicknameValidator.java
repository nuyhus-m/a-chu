package com.ssafy.s12p21d206.achu.api.validation;

public class BabyNicknameValidator extends RegexValidator<BabyNickname> {
  private static final String REGEX = "^[a-zA-Z0-9가-힣]{2,6}$";

  @Override
  protected String getRegex(BabyNickname constraintAnnotation) {
    return REGEX;
  }
}
