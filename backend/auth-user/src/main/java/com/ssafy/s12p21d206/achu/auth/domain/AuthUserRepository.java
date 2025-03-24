package com.ssafy.s12p21d206.achu.auth.domain;

public interface AuthUserRepository {

  boolean existsByNickname(String nickname);

  boolean existsByUsername(String username);

  AuthUser save(NewAuthUser newAuthUser);

  AuthUser modifyNickname(Long userId, String nickname);
}
