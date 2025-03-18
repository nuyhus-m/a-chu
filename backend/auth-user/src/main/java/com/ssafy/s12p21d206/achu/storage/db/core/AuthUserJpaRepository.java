package com.ssafy.s12p21d206.achu.storage.db.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserJpaRepository extends JpaRepository<AuthUserEntity, Long> {

  boolean existsByNickname(String nickname);

  boolean existsByUsername(String username);
}
