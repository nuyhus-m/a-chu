package com.ssafy.s12p21d206.achu.fcm.controller;

import com.ssafy.s12p21d206.achu.fcm.domain.FcmTokenService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FcmTokenController {

  private final FcmTokenService fcmTokenService;

  public FcmTokenController(FcmTokenService fcmTokenService) {
    this.fcmTokenService = fcmTokenService;
  }

  @PostMapping("/fcm-token")
  public void appendFcmToken(FcmApiUser apiUser, @RequestBody AppendFcmTokenRequest request) {
    fcmTokenService.append(apiUser.toFcmUser(), request.toNewFcmToken());
  }

  @DeleteMapping("/fcm-token")
  public void deleteFcmToken(FcmApiUser apiUser) {
    fcmTokenService.delete(apiUser.toFcmUser());
  }
}
