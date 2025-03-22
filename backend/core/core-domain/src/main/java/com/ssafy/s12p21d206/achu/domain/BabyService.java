package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BabyService {

  private final BabyAppender babyAppender;
  private final BabyReader babyReader;

  public BabyService(BabyAppender babyAppender, BabyReader babyReader) {
    this.babyAppender = babyAppender;
    this.babyReader = babyReader;
  }

  public Long append(User user, NewBaby newBaby) {
    Baby baby = babyAppender.append(user, newBaby);
    return baby.getId();
  }

  public List<Baby> findBabies(User user) {
    return babyReader.readBabies(user);
  }
}
