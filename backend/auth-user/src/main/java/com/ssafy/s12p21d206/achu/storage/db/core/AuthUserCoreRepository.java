package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.auth.domain.AuthUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuthUserCoreRepository implements AuthUserRepository {

  private final AuthUserJpaRepository authUserJpaRepository;

  public AuthUserCoreRepository(AuthUserJpaRepository authUserJpaRepository) {
    this.authUserJpaRepository = authUserJpaRepository;
  }

  @Override
  public boolean isNicknameUnique(String nickname) {
    return !authUserJpaRepository.existsByNickname(nickname);
  }

  @Override
  public boolean isUsernameUnique(String username) {
    return !authUserJpaRepository.existsByUsername(username);
  }
}
