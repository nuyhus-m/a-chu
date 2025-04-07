package com.ssafy.s12p21d206.achu.fcm.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenReader {

  private final FcmTokenRepository fcmTokenRepository;
  private static final Logger log = LoggerFactory.getLogger(FcmTokenReader.class);

  public FcmTokenReader(FcmTokenRepository fcmTokenRepository) {
    this.fcmTokenRepository = fcmTokenRepository;
  }

  public Optional<FcmToken> readUserToken(Long userId) {
    return fcmTokenRepository.findByUserId(userId);
  }

  public List<FcmToken> readUserTokens(Set<Long> userIds) {
    List<FcmToken> tokens = fcmTokenRepository.findByUserIds(userIds);
    Set<Long> foundUserIds = tokens.stream().map(FcmToken::userId).collect(Collectors.toSet());

    Set<Long> missingUserIds = new HashSet<>(userIds);
    missingUserIds.removeAll(foundUserIds);

    for (Long missingId : missingUserIds) {
      log.warn("📭 FCM 토큰 없음 → 알림 생략: userId={}", missingId);
    }

    return tokens;
  }
}
