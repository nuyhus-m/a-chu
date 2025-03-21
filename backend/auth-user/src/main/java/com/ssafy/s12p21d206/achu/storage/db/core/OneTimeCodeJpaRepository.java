package com.ssafy.s12p21d206.achu.storage.db.core;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimeCodeJpaRepository extends JpaRepository<OneTimeCodeEntity, UUID> {

  Optional<OneTimeCodeEntity> findTopByPhoneNumberAndEntityStatusOrderByCreatedAtDesc(
      String phoneNumber, AuthEntityStatus entityStatus);
}
