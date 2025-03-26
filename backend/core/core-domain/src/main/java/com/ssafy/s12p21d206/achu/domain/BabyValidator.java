package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class BabyValidator {
  private final BabyRepository babyRepository;

  public BabyValidator(BabyRepository babyRepository) {
    this.babyRepository = babyRepository;
  }

  public void validateExists(Long id) {
    if (!babyRepository.existsByIdAndEntityStatus(id)) {
      throw new CoreException(CoreErrorType.DATA_NOT_FOUND);
    }
  }

  public void validateParent(User user, Long id) {
    if (!babyRepository.existsByIdAndUser(id, user)) {
      throw new CoreException(CoreErrorType.CANNOT_ACCESS_BABY);
    }
  }
}
