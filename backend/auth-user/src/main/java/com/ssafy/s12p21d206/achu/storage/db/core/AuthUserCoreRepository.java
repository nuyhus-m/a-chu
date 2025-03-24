package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.auth.domain.AuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.AuthUserRepository;
import com.ssafy.s12p21d206.achu.auth.domain.NewAuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import org.springframework.stereotype.Repository;

@Repository
public class AuthUserCoreRepository implements AuthUserRepository {

  private final AuthUserJpaRepository authUserJpaRepository;

  public AuthUserCoreRepository(AuthUserJpaRepository authUserJpaRepository) {
    this.authUserJpaRepository = authUserJpaRepository;
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return authUserJpaRepository.existsByNickname(nickname);
  }

  @Override
  public boolean existsByUsername(String username) {
    return authUserJpaRepository.existsByUsername(username);
  }

  @Override
  public AuthUser save(NewAuthUser newAuthUser) {
    return authUserJpaRepository.save(AuthUserEntity.from(newAuthUser)).toAuthUser();
  }

  @Override
  public AuthUser modifyNickname(Long userId, String nickname) {
    AuthUserEntity authUserEntity = authUserJpaRepository
        .findById(userId)
        .orElseThrow(() -> new AuthCoreException(AuthCoreErrorType.DUPLICATED_NICKNAME));

    authUserEntity.changeNickname(nickname);

    authUserJpaRepository.save(authUserEntity);

    return authUserEntity.toAuthUser();
  }
}
