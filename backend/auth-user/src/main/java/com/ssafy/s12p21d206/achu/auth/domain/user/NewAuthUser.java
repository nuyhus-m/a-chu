package com.ssafy.s12p21d206.achu.auth.domain.user;

import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import org.springframework.security.crypto.password.PasswordEncoder;

public class NewAuthUser {

  private final String username;
  private String password;
  private final String nickname;
  private final Phone phone;

  private boolean isEncoded;

  public NewAuthUser(String username, String password, String nickname, Phone phone) {
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.phone = phone;
    this.isEncoded = false;
  }

  public void encodePassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
    isEncoded = true;
  }

  public String username() {
    return username;
  }

  public String password() {
    if (!isEncoded) {
      throw new AssertionError("password getter는 엔코드 후에 호출할 수 있습니다.");
    }
    return password;
  }

  public String nickname() {
    return nickname;
  }

  public Phone phone() {
    return phone;
  }
}
