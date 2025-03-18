package com.ssafy.s12p21d206.achu.auth.domain;

public interface AuthUserRepository {

  boolean isNicknameUnique(String nickname);
}
