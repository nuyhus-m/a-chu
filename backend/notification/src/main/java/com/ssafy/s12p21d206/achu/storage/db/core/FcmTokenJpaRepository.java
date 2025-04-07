package com.ssafy.s12p21d206.achu.storage.db.core;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenJpaRepository extends JpaRepository<FcmTokenEntity, Long> {
  Optional<FcmTokenEntity> findByUserIdAndEntityStatus(
      Long userId, FcmEntityStatus fcmEntityStatus);

  boolean existsByUserIdAndEntityStatus(Long userId, FcmEntityStatus fcmEntityStatus);

  List<FcmTokenEntity> findByUserIdInAndEntityStatus(
      Set<Long> userIds, FcmEntityStatus entityStatus);
}
