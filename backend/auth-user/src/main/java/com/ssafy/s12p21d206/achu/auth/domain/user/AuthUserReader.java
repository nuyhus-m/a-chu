package com.ssafy.s12p21d206.achu.auth.domain.user;

import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import org.springframework.stereotype.Component;

@Component
public class AuthUserReader {

  private final AuthUserRepository authUserRepository;

  public AuthUserReader(AuthUserRepository authUserRepository) {
    this.authUserRepository = authUserRepository;
  }

  public AuthUser findAuthUser(Long userId) {
    return authUserRepository
        .findById(userId)
        .orElseThrow(() -> new AuthCoreException(AuthCoreErrorType.USER_NOT_FOUND));
  }
}
