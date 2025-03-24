package com.ssafy.s12p21d206.achu.auth.domain.verification;

import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestIntervalValidator {

  @Value("${achu.verification-code.request-cooldown-seconds}")
  private int requestCooldownSeconds;

  private final VerificationCodeRepository verificationCodeRepository;

  public RequestIntervalValidator(VerificationCodeRepository verificationCodeRepository) {
    this.verificationCodeRepository = verificationCodeRepository;
  }

  public void validate(Phone phone) {
    Optional<VerificationCode> latestVerificationCode =
        verificationCodeRepository.findLatestByPhone(phone);

    if (latestVerificationCode.isPresent()) {
      VerificationCode latestCode = latestVerificationCode.get();
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime createdAt = latestCode.getDefaultDateTime().createdAt();

      long secondsSinceCreation = ChronoUnit.SECONDS.between(createdAt, now);

      if (secondsSinceCreation < requestCooldownSeconds) {
        long remainingSeconds = requestCooldownSeconds - secondsSinceCreation;
        throw new AuthCoreException(
            AuthCoreErrorType.VERIFICATION_REQUEST_TOO_FREQUENT, remainingSeconds);
      }
    }
  }
}
