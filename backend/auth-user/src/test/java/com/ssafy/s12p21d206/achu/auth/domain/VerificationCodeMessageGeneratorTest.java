package com.ssafy.s12p21d206.achu.auth.domain;

import static org.assertj.core.api.Assertions.*;

import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
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
    String verificationId = UUID.randomUUID().toString();
    String code = "123456";
    Duration expiresIn = Duration.ofMinutes(3);
    VerificationCode verificationCode = new VerificationCode(
        UUID.randomUUID(),
        new Phone("01012341234"),
        code,
        purpose,
        false,
        expiresIn,
        new AuthDefaultDateTime(LocalDateTime.now(), LocalDateTime.now()));

    // when
    String verificationCodeMessage = sut.generate(verificationCode, purpose);

    // then
    assertThat(verificationCodeMessage)
        .contains(code)
        .contains(purpose.getDescription())
        .contains(String.valueOf(expiresIn.getSeconds()));
  }
}
