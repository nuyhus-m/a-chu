package com.ssafy.s12p21d206.achu.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GoodsDescriptionValidator implements ConstraintValidator<GoodsDescription, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    String stripedValue = value.strip();
    if (stripedValue.length() != value.length()) {
      return false;
    }

    return !stripedValue.isEmpty() && stripedValue.length() <= 200;
  }
}
