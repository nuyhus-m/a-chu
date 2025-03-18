package com.ssafy.s12p21d206.achu.storage.db.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "`user`")
@Entity
public class AuthUserEntity extends AuthBaseEntity {

  private String username;
  private String password;
  private String nickname;
  private String phoneNumber;

  protected AuthUserEntity() {}

  public AuthUserEntity(String username, String password, String nickname, String phoneNumber) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getNickname() {
    return nickname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
