package com.ssafy.s12p21d206.achu.fcm.controller;

import com.google.firebase.database.annotations.NotNull;
import com.ssafy.s12p21d206.achu.fcm.domain.NewFcmToken;

public record AppendFcmTokenRequest(@NotNull String fcmToken) {
  public NewFcmToken toNewFcmToken() {
    return new NewFcmToken(this.fcmToken);
  }
}
