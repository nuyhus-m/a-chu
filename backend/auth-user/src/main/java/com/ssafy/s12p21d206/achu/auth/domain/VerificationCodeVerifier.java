package com.ssafy.s12p21d206.achu.auth.domain;

import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeVerifier {

  private final VerificationCodeRepository verificationCodeRepository;

  public VerificationCodeVerifier(VerificationCodeRepository verificationCodeRepository) {
    this.verificationCodeRepository = verificationCodeRepository;
  }

  public void verify(UUID verificationCodeId, String code) {
    VerificationCode verificationCode = verificationCodeRepository
        .findById(verificationCodeId)
        .orElseThrow(() -> new AuthCoreException(AuthCoreErrorType.INVALID_VERIFICATION_CODE));
    verificationCode.verify(code);
    verificationCodeRepository.update(verificationCode);
  }
}
