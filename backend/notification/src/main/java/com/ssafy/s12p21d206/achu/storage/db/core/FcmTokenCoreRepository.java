package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.fcm.domain.FcmToken;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmTokenRepository;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmUser;
import com.ssafy.s12p21d206.achu.fcm.domain.NewFcmToken;
import com.ssafy.s12p21d206.achu.fcm.domain.error.FcmErrorType;
import com.ssafy.s12p21d206.achu.fcm.domain.error.FcmException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class FcmTokenCoreRepository implements FcmTokenRepository {

  private final FcmTokenJpaRepository fcmTokenJpaRepository;

  public FcmTokenCoreRepository(FcmTokenJpaRepository fcmTokenJpaRepository) {
    this.fcmTokenJpaRepository = fcmTokenJpaRepository;
  }

  @Override
  public FcmToken save(FcmUser user, NewFcmToken newFcmToken) {
    return fcmTokenJpaRepository
        .save(new FcmTokenEntity(user.id(), newFcmToken.fcmToken()))
        .toFcmToken();
  }

  @Override
  public Optional<FcmToken> findByUserId(Long userId) {
    return fcmTokenJpaRepository
        .findByUserIdAndEntityStatus(userId, FcmEntityStatus.ACTIVE)
        .map(FcmTokenEntity::toFcmToken);
  }

  @Override
  public boolean existsByUserId(Long userId) {
    return fcmTokenJpaRepository.existsByUserIdAndEntityStatus(userId, FcmEntityStatus.ACTIVE);
  }

  @Transactional
  @Override
  public FcmToken update(Long userId, NewFcmToken newFcmToken) {
    FcmTokenEntity fcmTokenEntity = fcmTokenJpaRepository
        .findByUserIdAndEntityStatus(userId, FcmEntityStatus.ACTIVE)
        .orElseThrow(() -> new FcmException(FcmErrorType.DATA_NOT_FOUND));
    fcmTokenEntity.update(newFcmToken);
    return fcmTokenEntity.toFcmToken();
  }

  @Transactional
  @Override
  public void delete(Long userId) {
    FcmTokenEntity fcmTokenEntity = fcmTokenJpaRepository
        .findByUserIdAndEntityStatus(userId, FcmEntityStatus.ACTIVE)
        .orElseThrow(() -> new FcmException(FcmErrorType.DATA_NOT_FOUND));
    fcmTokenEntity.delete();
  }

  @Override
  public List<FcmToken> findByUserIds(Set<Long> userIds) {
    List<FcmTokenEntity> fcmTokenEntities =
        fcmTokenJpaRepository.findByUserIdInAndEntityStatus(userIds, FcmEntityStatus.ACTIVE);
    return fcmTokenEntities.stream().map(FcmTokenEntity::toFcmToken).toList();
  }
}
