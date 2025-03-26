package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class BabyDeleter {

  private final BabyRepository babyRepository;
  private final BabyValidator babyValidator;

  public BabyDeleter(BabyRepository babyRepository, BabyValidator babyValidator) {
    this.babyRepository = babyRepository;
    this.babyValidator = babyValidator;
  }

  public Long delete(Long id) {
    babyValidator.validateExists(id);
    return babyRepository.delete(id);
  }
}
