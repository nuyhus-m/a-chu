package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BabyReader {

  private final BabyRepository babyRepository;

  public BabyReader(BabyRepository babyRepository) {
    this.babyRepository = babyRepository;
  }

  public Baby readBaby(Long id) {
    return babyRepository
        .findById(id)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND));
  }

  public List<Baby> readBabies(User user) {
    return babyRepository.findByUser(user);
  }
}
