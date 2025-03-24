package com.ssafy.s12p21d206.achu.auth.domain;

import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;

public class AuthUser {

  private final Long id;
  private final String username;
  private final String password;
  private final String nickname;
  private final Phone phone;
  private final AuthDefaultDateTime defaultDateTime;

  public AuthUser(
      Long id,
      String username,
      String password,
      String nickname,
      Phone phone,
      AuthDefaultDateTime defaultDateTime) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.phone = phone;
    this.defaultDateTime = defaultDateTime;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getNickname() {
    return nickname;
  }

  public Phone getPhone() {
    return phone;
  }

  public AuthDefaultDateTime defaultDateTime() {
    return defaultDateTime;
  }
}
