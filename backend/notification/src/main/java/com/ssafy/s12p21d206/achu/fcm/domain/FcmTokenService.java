package com.ssafy.s12p21d206.achu.fcm.domain;

import org.springframework.stereotype.Service;

@Service
public class FcmTokenService {

  private final FcmTokenAppender fcmTokenAppender;
  private final FcmTokenDeleter fcmTokenDeleter;

  public FcmTokenService(FcmTokenAppender fcmTokenAppender, FcmTokenDeleter fcmTokenDeleter) {
    this.fcmTokenAppender = fcmTokenAppender;
    this.fcmTokenDeleter = fcmTokenDeleter;
  }

  public FcmToken append(FcmUser user, NewFcmToken newFcmToken) {
    return fcmTokenAppender.append(user, newFcmToken);
  }

  public void delete(FcmUser user) {
    fcmTokenDeleter.delete(user);
  }
}
