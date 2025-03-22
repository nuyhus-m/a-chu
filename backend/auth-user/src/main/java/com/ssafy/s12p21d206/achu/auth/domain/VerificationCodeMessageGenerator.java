package com.ssafy.s12p21d206.achu.auth.domain;

import org.springframework.stereotype.Component;

@Component
public class VerificationCodeMessageGenerator {

  public String generate(VerificationCode verificationCode, VerificationPurpose purpose) {
    return String.format(
        """
                A-Chu %s 인증번호는 [%s] 입니다.
                %d초 안에 입력해 주세요.""",
        purpose.getDescription(),
        verificationCode.code(),
        verificationCode.expiresIn().toSeconds());
  }
}
