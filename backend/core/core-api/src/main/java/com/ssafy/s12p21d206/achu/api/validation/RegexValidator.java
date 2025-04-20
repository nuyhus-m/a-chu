package com.ssafy.s12p21d206.achu.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

public abstract class RegexValidator<T extends Annotation>
    implements ConstraintValidator<T, String> {
  protected Pattern pattern;

  @Override
  public void initialize(T constraintAnnotation) {
    String regex = getRegex(constraintAnnotation);
    this.pattern = Pattern.compile(regex);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    return pattern.matcher(value).matches();
  }

  protected abstract String getRegex(T constraintAnnotation);
}
