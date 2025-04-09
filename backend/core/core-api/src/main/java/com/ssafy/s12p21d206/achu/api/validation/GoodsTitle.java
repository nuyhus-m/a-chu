package com.ssafy.s12p21d206.achu.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GoodsTitleValidator.class)
public @interface GoodsTitle {
  String message() default "물건 등록 시 제목은 한글, 영어, 숫자, 특수문자 1~20자여야 하며, 앞뒤 공백과 이모지는 허용되지 않습니다. ";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
