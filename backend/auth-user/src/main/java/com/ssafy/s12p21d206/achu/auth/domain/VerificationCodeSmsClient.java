package com.ssafy.s12p21d206.achu.auth.domain;

public interface VerificationCodeSmsClient {

  void send(String text, Phone phone);
}
