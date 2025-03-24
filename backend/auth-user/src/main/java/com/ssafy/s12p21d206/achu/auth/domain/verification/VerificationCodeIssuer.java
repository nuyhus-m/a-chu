package com.ssafy.s12p21d206.achu.auth.domain.verification;

import org.springframework.stereotype.Component;

@Component
public class VerificationCodeIssuer {

  private final RequestIntervalValidator requestIntervalValidator;
  private final VerificationCodeGenerator verificationCodeGenerator;
  private final VerificationCodeRepository verificationCodeRepository;

  public VerificationCodeIssuer(
      RequestIntervalValidator requestIntervalValidator,
      VerificationCodeGenerator verificationCodeGenerator,
      VerificationCodeRepository verificationCodeRepository) {
    this.requestIntervalValidator = requestIntervalValidator;
    this.verificationCodeGenerator = verificationCodeGenerator;
    this.verificationCodeRepository = verificationCodeRepository;
  }

  /**
   * 인증번호를 생성하고 저장합니다.
   */
  public VerificationCode issue(Phone phone, VerificationPurpose purpose) {
    requestIntervalValidator.validate(phone);
    NewVerificationCode newVerificationCode = verificationCodeGenerator.generate();
    return verificationCodeRepository.save(newVerificationCode, phone, purpose);
  }
}
