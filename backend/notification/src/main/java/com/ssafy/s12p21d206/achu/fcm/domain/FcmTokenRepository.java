package com.ssafy.s12p21d206.achu.fcm.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FcmTokenRepository {

  FcmToken save(FcmUser user, NewFcmToken newFcmToken);

  boolean existsByUserId(Long userId);

  Optional<FcmToken> findByUserId(Long userId);

  FcmToken update(Long userId, NewFcmToken newFcmToken);

  void delete(Long userId);

  List<FcmToken> findByUserIds(Set<Long> userIds);
}
