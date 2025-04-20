package com.ssafy.s12p21d206.achu.fcm.controller;

import com.ssafy.s12p21d206.achu.fcm.domain.FcmUser;

public record FcmApiUser(Long id) {
  public FcmUser toFcmUser() {
    return new FcmUser(this.id);
  }
}
