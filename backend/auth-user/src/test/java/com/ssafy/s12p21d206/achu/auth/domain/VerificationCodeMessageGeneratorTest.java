package com.ssafy.s12p21d206.achu.auth.domain;

import static org.assertj.core.api.Assertions.*;

import com.ssafy.s12p21d206.achu.builder.VerificationCodeBuilder;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VerificationCodeMessageGeneratorTest {

  private VerificationCodeMessageGenerator sut;

  @BeforeEach
  void setUp() {
    sut = new VerificationCodeMessageGenerator();
  }

  @Test
  void 회원_가입_인증_메세지를_생성_할_수_있다() {
    // given
    VerificationPurpose purpose = VerificationPurpose.SIGN_UP;
    VerificationCode verificationCode =
        VerificationCodeBuilder.createNonVerified(purpose, LocalDateTime.now());
    // when
    String verificationCodeMessage = sut.generate(verificationCode, purpose);

    // then
    assertThat(verificationCodeMessage)
        .contains(verificationCode.getCode())
        .contains(verificationCode.getPurpose().getDescription())
        .contains(String.valueOf(verificationCode.getExpiresIn().getSeconds()));
  }
}
