package com.ssafy.s12p21d206.achu.storage.db.core;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserJpaRepository extends JpaRepository<AuthUserEntity, Long> {

  boolean existsByNicknameAndEntityStatus(String nickname, AuthEntityStatus status);

  boolean existsByUsernameAndEntityStatus(String username, AuthEntityStatus status);

  Optional<AuthUserEntity> findByUsernameAndEntityStatus(String username, AuthEntityStatus status);

  Optional<AuthUserEntity> findByIdAndEntityStatus(Long id, AuthEntityStatus status);
}
