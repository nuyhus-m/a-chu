package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class BabyDeleter {

  private final BabyRepository babyRepository;

  public BabyDeleter(BabyRepository babyRepository) {
    this.babyRepository = babyRepository;
  }

  public Long delete(Long id) {
    return babyRepository.delete(id);
  }
}
