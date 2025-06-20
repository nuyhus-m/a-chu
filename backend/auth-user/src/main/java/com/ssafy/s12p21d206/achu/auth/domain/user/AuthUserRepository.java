package com.ssafy.s12p21d206.achu.auth.domain.user;

import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import java.util.Optional;

public interface AuthUserRepository {

  boolean existsByNickname(String nickname);

  boolean existsByUsername(String username);

  AuthUser save(NewAuthUser newAuthUser);

  AuthUser modifyNickname(Long userId, String nickname);

  Optional<AuthUser> findByUsername(String username);

  Optional<AuthUser> findById(Long userId);

  AuthUser modifyPassword(Long userId, String encodedPassword);

  AuthUser modifyPhoneNumber(Long userId, Phone phone);

  AuthUser modifyProfileImage(Long userId, String profileImageUrl);
}
