package com.ssafy.s12p21d206.achu.auth.domain.verification;

public enum VerificationPurpose {
  SIGN_UP("회원가입"),
  CHANGE_PASSWORD("비밀번호 변경"),
  RESET_PASSWORD("비밀번호 초기화"),
  CHANGE_PHONE_NUMBER("전화번호 변경");

  private final String description;

  VerificationPurpose(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
