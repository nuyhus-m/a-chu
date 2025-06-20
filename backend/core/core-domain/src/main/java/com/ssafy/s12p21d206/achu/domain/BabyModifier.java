package com.ssafy.s12p21d206.achu.domain;

import java.time.LocalDate;
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

  public Baby modifyBirth(Long id, LocalDate birth) {
    return babyRepository.modifyBirth(id, birth);
  }

  public Baby modifyGender(Long id, Sex gender) {
    return babyRepository.modifyGender(id, gender);
  }

  public Baby modifyImageUrl(Long id, String profileImageUrl) {
    return babyRepository.modifyImageUrl(id, profileImageUrl);
  }
}
