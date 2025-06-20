package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationPurpose;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeCodeJpaRepository extends JpaRepository<OneTimeCodeEntity, UUID> {

  Optional<OneTimeCodeEntity> findTopByPhoneNumberAndEntityStatusOrderByCreatedAtDesc(
      String phoneNumber, AuthEntityStatus entityStatus);

  Optional<OneTimeCodeEntity> findByIdAndEntityStatus(UUID id, AuthEntityStatus entityStatus);

  Optional<OneTimeCodeEntity> findByIdAndPurposeAndEntityStatus(
      UUID id, VerificationPurpose purpose, AuthEntityStatus entityStatus);
}
