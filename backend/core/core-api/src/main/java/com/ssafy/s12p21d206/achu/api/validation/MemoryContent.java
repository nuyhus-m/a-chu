package com.ssafy.s12p21d206.achu.api.validation;

import jakarta.validation.Payload;

public @interface MemoryContent {

  String message() default "추억 등록 시 내용은 한글, 영어, 숫자, 특수문자 포함 2~100자여야 하며, 앞뒤 공백과 이모지는 허용되지 않습니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
