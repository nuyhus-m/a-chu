package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class BabyAppender {

  private final BabyRepository babyRepository;

  public BabyAppender(BabyRepository babyRepository) {
    this.babyRepository = babyRepository;
  }

  public Baby append(User user, NewBaby newBaby) {
    return babyRepository.save(user, newBaby);
  }
}
