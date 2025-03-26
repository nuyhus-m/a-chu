package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUserRepository;
import com.ssafy.s12p21d206.achu.auth.domain.user.NewAuthUser;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AuthUserCoreRepository implements AuthUserRepository {

  private final AuthUserJpaRepository authUserJpaRepository;

  public AuthUserCoreRepository(AuthUserJpaRepository authUserJpaRepository) {
    this.authUserJpaRepository = authUserJpaRepository;
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return authUserJpaRepository.existsByNicknameAndEntityStatus(nickname, AuthEntityStatus.ACTIVE);
  }

  @Override
  public boolean existsByUsername(String username) {
    return authUserJpaRepository.existsByUsernameAndEntityStatus(username, AuthEntityStatus.ACTIVE);
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

  @Override
  public Optional<AuthUser> findByUsername(String username) {
    return authUserJpaRepository
        .findByUsernameAndEntityStatus(username, AuthEntityStatus.ACTIVE)
        .map(AuthUserEntity::toAuthUser);
  }
}
