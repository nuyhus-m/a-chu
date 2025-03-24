package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.ValidationUtils;

public record ModifyNicknameRequest(String nickname) {
  public String nickname() {
    ValidationUtils.validateNickname(nickname);
    return nickname;
  }
}
