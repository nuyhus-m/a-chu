package com.ssafy.s12p21d206.achu.auth.domain.verification;

import com.ssafy.s12p21d206.achu.auth.domain.Phone;

public interface VerificationCodeSmsClient {

  void send(String text, Phone phone);
}
