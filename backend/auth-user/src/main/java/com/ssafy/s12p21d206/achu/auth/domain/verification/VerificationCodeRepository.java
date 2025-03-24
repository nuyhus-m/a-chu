package com.ssafy.s12p21d206.achu.auth.domain.verification;

import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository {

  VerificationCode save(
      NewVerificationCode verificationCode, Phone phone, VerificationPurpose purpose);

  Optional<VerificationCode> findLatestByPhone(Phone phone);

  Optional<VerificationCode> findById(UUID id);

  void update(VerificationCode verificationCode);

  Optional<VerificationCode> findByIdAndPurpose(
      UUID verificationCodeId, VerificationPurpose purpose);
}
