package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BabyService {

  private final BabyAppender babyAppender;
  private final BabyReader babyReader;
  private final BabyDeleter babyDeleter;
  private final BabyModifier babyModifier;
  private final BabyValidator babyValidator;

  public BabyService(
      BabyAppender babyAppender,
      BabyReader babyReader,
      BabyDeleter babyDeleter,
      BabyModifier babyModifier,
      BabyValidator babyValidator) {
    this.babyAppender = babyAppender;
    this.babyReader = babyReader;
    this.babyDeleter = babyDeleter;
    this.babyModifier = babyModifier;
    this.babyValidator = babyValidator;
  }

  public Long append(User user, NewBaby newBaby) {
    Baby baby = babyAppender.append(user, newBaby);
    return baby.getId();
  }

  public Baby findBaby(User user, Long id) {
    babyValidator.validateParent(user, id);
    return babyReader.readBaby(id);
  }

  public List<Baby> findBabies(User user) {
    return babyReader.readBabies(user);
  }

  public Long delete(User user, Long id) {
    babyValidator.validateParent(user, id);
    return babyDeleter.delete(id);
  }

  public Baby modifyNickname(User user, Long id, String nickname) {
    babyValidator.validateExists(id);
    babyValidator.validateParent(user, id);
    return babyModifier.modifyNickname(id, nickname);
  }
}
