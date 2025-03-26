package com.ssafy.s12p21d206.achu.auth.domain.user;

import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;
import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import org.springframework.security.crypto.password.PasswordEncoder;

public record AuthUser(
    Long id,
    String username,
    String password,
    String nickname,
    Phone phone,
    AuthDefaultDateTime defaultDateTime) {

  public void validatePassword(PasswordEncoder passwordEncoder, String password) {
    if (!passwordEncoder.matches(password, this.password)) {
      throw new AuthCoreException(AuthCoreErrorType.INVALID_CREDENTIALS);
    }
  }
}
