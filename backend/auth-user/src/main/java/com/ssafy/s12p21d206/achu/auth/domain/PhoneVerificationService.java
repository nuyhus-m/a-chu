package com.ssafy.s12p21d206.achu.auth.domain;

import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {

  private final VerificationCodeIssuer verificationCodeIssuer;
  private final VerificationCodeSender verificationCodeSender;

  public PhoneVerificationService(
      VerificationCodeIssuer verificationCodeIssuer,
      VerificationCodeSender verificationCodeSender) {
    this.verificationCodeIssuer = verificationCodeIssuer;
    this.verificationCodeSender = verificationCodeSender;
  }

  public VerificationCode issuePhoneVerificationCode(Phone phone, VerificationPurpose purpose) {
    VerificationCode verificationCode = verificationCodeIssuer.issue(phone, purpose);
    verificationCodeSender.push(verificationCode, phone, purpose);
    return verificationCode;
  }
}
