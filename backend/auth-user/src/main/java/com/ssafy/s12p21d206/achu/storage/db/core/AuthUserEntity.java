package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;
import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.user.NewAuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "`user`")
@Entity
public class AuthUserEntity extends AuthBaseEntity {

  private String username;
  private String password;
  private String nickname;
  private String phoneNumber;
  private String profileImageUrl;

  protected AuthUserEntity() {}

  public AuthUserEntity(
      String username,
      String password,
      String nickname,
      String phoneNumber,
      String profileImageUrl) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.phoneNumber = phoneNumber;
    this.profileImageUrl = profileImageUrl;
  }

  public static AuthUserEntity from(NewAuthUser newAuthUser) {
    return new AuthUserEntity(
        newAuthUser.username(),
        newAuthUser.password(),
        newAuthUser.nickname(),
        newAuthUser.phone().number(),
        null);
  }

  public AuthUser toAuthUser() {
    return new AuthUser(
        this.getId(),
        this.username,
        this.password,
        this.nickname,
        new Phone(this.phoneNumber),
        this.profileImageUrl,
        new AuthDefaultDateTime(this.getCreatedAt(), this.getUpdatedAt()));
  }

  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }

  public void changePassword(String password) {
    this.password = password;
  }

  public void changePhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void changeProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }
}
