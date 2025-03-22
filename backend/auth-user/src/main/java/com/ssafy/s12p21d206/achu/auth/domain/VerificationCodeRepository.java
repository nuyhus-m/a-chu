package com.ssafy.s12p21d206.achu.auth.domain;

import java.util.Optional;

public interface VerificationCodeRepository {

  VerificationCode save(
      NewVerificationCode verificationCode, Phone phone, VerificationPurpose purpose);

  Optional<VerificationCode> findLatestByPhone(Phone phone);
}
