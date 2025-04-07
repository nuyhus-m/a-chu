package com.ssafy.s12p21d206.achu.fcm.domain;

import org.springframework.stereotype.Component;

@Component
public class FcmTokenAppender {
  private final FcmTokenRepository fcmTokenRepository;

  public FcmTokenAppender(FcmTokenRepository fcmTokenRepository) {
    this.fcmTokenRepository = fcmTokenRepository;
  }

  public FcmToken append(FcmUser user, NewFcmToken newFcmToken) {
    return fcmTokenRepository
        .findByUserId(user.id())
        .map(existingToken -> {
          if (existingToken.fcmToken().equals(newFcmToken.fcmToken())) {
            return existingToken;
          } else {
            return fcmTokenRepository.update(user.id(), newFcmToken);
          }
        })
        .orElseGet(() -> fcmTokenRepository.save(user, newFcmToken));
  }
}
