package com.ssafy.s12p21d206.achu.auth.domain;

import org.springframework.stereotype.Component;

@Component
public class VerificationCodeSender {

  private final VerificationCodeSmsClient verificationCodeSmsClient;
  private final VerificationCodeMessageGenerator verificationCodeMessageGenerator;

  public VerificationCodeSender(
      VerificationCodeSmsClient verificationCodeSmsClient,
      VerificationCodeMessageGenerator verificationCodeMessageGenerator) {
    this.verificationCodeSmsClient = verificationCodeSmsClient;
    this.verificationCodeMessageGenerator = verificationCodeMessageGenerator;
  }

  public void push(VerificationCode verificationCode, Phone phone, VerificationPurpose purpose) {
    String verificationCodeMessage =
        verificationCodeMessageGenerator.generate(verificationCode, purpose);
    verificationCodeSmsClient.send(verificationCodeMessage, phone);
  }
}
