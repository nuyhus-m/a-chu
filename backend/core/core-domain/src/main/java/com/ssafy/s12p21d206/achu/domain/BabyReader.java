package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BabyReader {

  private final BabyRepository babyRepository;

  public BabyReader(BabyRepository babyRepository) {
    this.babyRepository = babyRepository;
  }

  public List<Baby> readBabies(User user) {
    return babyRepository.findByUser(user);
  }
}
