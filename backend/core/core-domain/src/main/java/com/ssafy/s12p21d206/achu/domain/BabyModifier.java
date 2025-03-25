package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class BabyModifier {

  private final BabyRepository babyRepository;

  public BabyModifier(BabyRepository babyRepository) {
    this.babyRepository = babyRepository;
  }

  public Baby modifyNickname(Long id, String nickname) {
    return babyRepository.modifyNickname(id, nickname);
  }
}
