package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BabyService {

  private final BabyAppender babyAppender;
  private final BabyReader babyReader;
  private final BabyDeleter babyDeleter;

  public BabyService(BabyAppender babyAppender, BabyReader babyReader, BabyDeleter babyDeleter) {
    this.babyAppender = babyAppender;
    this.babyReader = babyReader;
    this.babyDeleter = babyDeleter;
  }

  public Long append(User user, NewBaby newBaby) {
    Baby baby = babyAppender.append(user, newBaby);
    return baby.getId();
  }

  public Baby findBaby(Long id) {
    return babyReader.readBaby(id);
  }

  public List<Baby> findBabies(User user) {
    return babyReader.readBabies(user);
  }

  public Long delete(Long id) {
    return babyDeleter.delete(id);
  }
}
