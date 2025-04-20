package com.ssafy.s12p21d206.achu.storage.db.core;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, String> {

  Optional<RefreshTokenEntity> findByUsernameAndExpirationTimeAfter(
      String username, LocalDateTime now);
}
