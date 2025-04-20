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
    // client에서 가격변동 알림에서 접속 시 babyId를 0으로 보내기로함
    // TODO: 추후에 가격변동 알림에서 아기 정보도 함께 보내는 것으로 변경해야함
    if (id == 0) {
      return;
    }

    if (!babyRepository.existsByIdAndUser(id, user)) {
      throw new CoreException(CoreErrorType.CANNOT_ACCESS_BABY);
    }
  }
}
