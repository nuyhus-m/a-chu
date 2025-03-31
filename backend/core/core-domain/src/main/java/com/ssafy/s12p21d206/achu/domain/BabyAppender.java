package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class BabyAppender {

  private final BabyRepository babyRepository;

  public BabyAppender(BabyRepository babyRepository) {
    this.babyRepository = babyRepository;
  }

  public Baby append(User user, NewBaby newBaby, String imageUrl) {
    if (babyRepository.countByUserId(user.id()) > 15) {
      throw new CoreException(CoreErrorType.CANNOT_REGISTER_MORE_BABIES);
    }

    return babyRepository.save(user, newBaby, imageUrl);
  }
}
