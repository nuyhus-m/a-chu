package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Baby;
import com.ssafy.s12p21d206.achu.domain.BabyRepository;
import com.ssafy.s12p21d206.achu.domain.NewBaby;
import com.ssafy.s12p21d206.achu.domain.User;
import java.util.List;
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
    return babyJpaRepository.findByUserId(user.id()).stream()
        .map(BabyEntity::toBaby)
        .toList();
  }
}
