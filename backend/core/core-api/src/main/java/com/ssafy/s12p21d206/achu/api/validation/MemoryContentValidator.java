package com.ssafy.s12p21d206.achu.api.validation;

public class MemoryContentValidator extends RegexValidator<MemoryContent> {

  private static final String REGEX =
      "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣\\p{P}\\p{Zs}+=`~\\\\\\$\\^<>\\\"']{1,100}$";

  @Override
  public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
    if (value == null) return false;

    if (!value.equals(value.strip())) return false;

    if (value.strip().isBlank()) return false;

    return pattern.matcher(value).matches();
  }

  @Override
  protected String getRegex(MemoryContent constraintAnnotation) {
    return REGEX;
  }
}
