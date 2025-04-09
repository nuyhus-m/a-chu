package com.ssafy.s12p21d206.achu.fcm.domain;

import com.google.firebase.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FcmNotifier {
  private final FirebaseMessaging firebaseMessaging;
  private final FcmTokenDeleter fcmTokenDeleter;
  private static final Logger log = LoggerFactory.getLogger(FcmNotifier.class);

  public FcmNotifier(FirebaseMessaging firebaseMessaging, FcmTokenDeleter fcmTokenDeleter) {
    this.firebaseMessaging = firebaseMessaging;
    this.fcmTokenDeleter = fcmTokenDeleter;
  }

  public void send(FcmMessage message, Long userId) {
    Notification notification =
        Notification.builder().setTitle(message.title()).setBody(message.body()).build();

    Message firebaseMessage = Message.builder()
        .setToken(message.token())
        .setNotification(notification)
        .putAllData(message.data())
        .build();

    try {
      firebaseMessaging.send(firebaseMessage);
      log.info("✅ FCM 전송 성공: userId={}, token={}, message={}", userId, message.token(), message);
    } catch (FirebaseMessagingException e) {
      var errorCode = e.getMessagingErrorCode();

      if (errorCode == MessagingErrorCode.UNREGISTERED
          || errorCode == MessagingErrorCode.INVALID_ARGUMENT) {
        log.warn("❌ 유효하지 않은 FCM 토큰 → 삭제: userId={}, token={}", userId, message.token());
        fcmTokenDeleter.delete(new FcmUser(userId));
      } else {
        log.error(
            "🔥 FCM 전송 실패: userId={}, token={}, error={}",
            userId,
            message.token(),
            e.getMessage(),
            e);
      }
    }
  }
}
