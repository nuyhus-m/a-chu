package com.ssafy.s12p21d206.achu.api.validation;

public class MemoryTitleValidator extends RegexValidator<MemoryTitle> {

  private static final String REGEX =
      "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣\\p{P}\\p{So}\\p{Zs}+=`~\\\\\\$\\^<>\\\"']{2,15}$";

  @Override
  public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
    if (value == null) return false;

    if (!value.equals(value.strip())) return false;

    if (value.strip().isBlank()) return false;

    return pattern.matcher(value).matches();
  }

  @Override
  protected String getRegex(MemoryTitle constraintAnnotation) {
    return REGEX;
  }
}
