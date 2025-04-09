package com.ssafy.s12p21d206.achu.api.validation;

import jakarta.validation.Payload;

public @interface MemoryContent {

  String message() default "추억 등록 시 내용은 앞뒤 공백 없는 1~100자여야 합니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
