package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Baby;
import com.ssafy.s12p21d206.achu.domain.BabyRepository;
import com.ssafy.s12p21d206.achu.domain.NewBaby;
import com.ssafy.s12p21d206.achu.domain.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BabyCoreRepository implements BabyRepository {

  private final BabyJpaRepository babyJpaRepository;

  public BabyCoreRepository(BabyJpaRepository babyJpaRepository) {
    this.babyJpaRepository = babyJpaRepository;
  }

  @Override
  public Baby save(User user, NewBaby newBaby) {
    return babyJpaRepository
        .save(new BabyEntity(
            user.id(), newBaby.nickname(), newBaby.gender(), newBaby.imageUrl(), newBaby.birth()))
        .toBaby();
  }

  @Override
  public List<Baby> findByUser(User user) {
    return babyJpaRepository.findByUserIdAndEntityStatus(user.id(), EntityStatus.ACTIVE).stream()
        .map(BabyEntity::toBaby)
        .toList();
  }

  @Override
  public Optional<Baby> findById(Long id) {
    return babyJpaRepository
        .findByIdAndEntityStatus(id, EntityStatus.ACTIVE)
        .map(BabyEntity::toBaby);
  }

  @Transactional
  @Override
  public Long delete(Long id) {
    Optional<BabyEntity> optionalEntity =
        babyJpaRepository.findByIdAndEntityStatus(id, EntityStatus.ACTIVE);

    if (optionalEntity.isEmpty()) {
      return -1L;
    }

    BabyEntity babyEntity = optionalEntity.get();
    babyEntity.delete();
    return babyEntity.getId();
  }

  @Override
  public boolean existsById(Long id) {
    return babyJpaRepository.existsById(id);
  }

  @Override
  public boolean existsByIdAndUser(Long id, User user) {
    return babyJpaRepository.existsByIdAndUserId(id, user.id());
  }
}
