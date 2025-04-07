package com.ssafy.s12p21d206.achu.fcm.domain;

import org.springframework.stereotype.Component;

@Component
public class FcmTokenDeleter {
  private final FcmTokenRepository fcmTokenRepository;
  private final FcmTokenValidator fcmTokenValidator;

  public FcmTokenDeleter(
      FcmTokenRepository fcmTokenRepository, FcmTokenValidator fcmTokenValidator) {
    this.fcmTokenRepository = fcmTokenRepository;
    this.fcmTokenValidator = fcmTokenValidator;
  }

  public void delete(FcmUser user) {
    fcmTokenValidator.validateExists(user.id());
    fcmTokenRepository.delete(user.id());
  }
}
