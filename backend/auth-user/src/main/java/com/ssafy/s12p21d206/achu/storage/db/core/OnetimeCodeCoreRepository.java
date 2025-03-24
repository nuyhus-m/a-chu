package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.auth.domain.NewVerificationCode;
import com.ssafy.s12p21d206.achu.auth.domain.Phone;
import com.ssafy.s12p21d206.achu.auth.domain.VerificationCode;
import com.ssafy.s12p21d206.achu.auth.domain.VerificationCodeRepository;
import com.ssafy.s12p21d206.achu.auth.domain.VerificationPurpose;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class OnetimeCodeCoreRepository implements VerificationCodeRepository {

  private final OneTimeCodeJpaRepository oneTimeCodeJpaRepository;

  public OnetimeCodeCoreRepository(OneTimeCodeJpaRepository oneTimeCodeJpaRepository) {
    this.oneTimeCodeJpaRepository = oneTimeCodeJpaRepository;
  }

  @Override
  public VerificationCode save(
      NewVerificationCode verificationCode, Phone phone, VerificationPurpose purpose) {
    return oneTimeCodeJpaRepository
        .save(OneTimeCodeEntity.from(verificationCode, phone, purpose))
        .toVerificationCode();
  }

  @Override
  public Optional<VerificationCode> findLatestByPhone(Phone phone) {
    return oneTimeCodeJpaRepository
        .findTopByPhoneNumberAndEntityStatusOrderByCreatedAtDesc(
            phone.number(), AuthEntityStatus.ACTIVE)
        .map(OneTimeCodeEntity::toVerificationCode);
  }

  @Override
  public Optional<VerificationCode> findById(UUID id) {
    return oneTimeCodeJpaRepository
        .findByIdAndEntityStatus(id, AuthEntityStatus.ACTIVE)
        .map(OneTimeCodeEntity::toVerificationCode);
  }

  @Override
  public void update(VerificationCode verificationCode) {
    oneTimeCodeJpaRepository.save(OneTimeCodeEntity.from(verificationCode));
  }

  @Override
  public Optional<VerificationCode> findByIdAndPurpose(UUID id, VerificationPurpose purpose) {
    return oneTimeCodeJpaRepository
        .findByIdAndPurposeAndEntityStatus(id, purpose, AuthEntityStatus.ACTIVE)
        .map(OneTimeCodeEntity::toVerificationCode);
  }
}
