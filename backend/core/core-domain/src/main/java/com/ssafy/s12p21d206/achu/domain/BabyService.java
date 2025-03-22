package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Service;

@Service
public class BabyService {

  private final BabyAppender babyAppender;

  public BabyService(BabyAppender babyAppender) {
    this.babyAppender = babyAppender;
  }

  public Long append(User user, NewBaby newBaby) {
    Baby baby = babyAppender.append(user, newBaby);
    return baby.getId();
  }
}
