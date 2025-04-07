package com.ssafy.s12p21d206.achu.fcm.domain;

import com.ssafy.s12p21d206.achu.fcm.domain.error.FcmErrorType;
import com.ssafy.s12p21d206.achu.fcm.domain.error.FcmException;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenValidator {
  private final FcmTokenRepository fcmTokenRepository;

  public FcmTokenValidator(FcmTokenRepository fcmTokenRepository) {
    this.fcmTokenRepository = fcmTokenRepository;
  }

  public boolean exists(Long userId) {
    return fcmTokenRepository.existsByUserId(userId);
  }

  public void validateExists(Long userId) {
    if (!fcmTokenRepository.existsByUserId(userId)) {
      throw new FcmException(FcmErrorType.DATA_NOT_FOUND);
    }
  }
}
