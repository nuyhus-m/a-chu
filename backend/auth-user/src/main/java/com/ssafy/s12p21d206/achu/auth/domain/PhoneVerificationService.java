package com.ssafy.s12p21d206.achu.auth.domain;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {

  private final VerificationCodeIssuer verificationCodeIssuer;
  private final VerificationCodeSender verificationCodeSender;
  private final VerificationCodeVerifier verificationCodeVerifier;

  public PhoneVerificationService(
      VerificationCodeIssuer verificationCodeIssuer,
      VerificationCodeSender verificationCodeSender,
      VerificationCodeVerifier verificationCodeVerifier) {
    this.verificationCodeIssuer = verificationCodeIssuer;
    this.verificationCodeSender = verificationCodeSender;
    this.verificationCodeVerifier = verificationCodeVerifier;
  }

  public VerificationCode issuePhoneVerificationCode(Phone phone, VerificationPurpose purpose) {
    VerificationCode verificationCode = verificationCodeIssuer.issue(phone, purpose);
    verificationCodeSender.push(verificationCode, phone, purpose);
    return verificationCode;
  }

  public void verifyPhoneVerificationCode(UUID verificationId, String code) {
    verificationCodeVerifier.verify(verificationId, code);
  }
}
