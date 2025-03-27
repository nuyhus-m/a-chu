package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class MemoryAppender {
  private final MemoryRepository memoryRepository;
  private final BabyValidator babyValidator;

  public MemoryAppender(MemoryRepository memoryRepository, BabyValidator babyValidator) {
    this.memoryRepository = memoryRepository;
    this.babyValidator = babyValidator;
  }

  public Memory save(User user, Long babyId, NewMemory newMemory) {
    //        여기서 검증하기
    babyValidator.validateExists(babyId);
    babyValidator.validateParent(user, babyId);
    //        존재하는 아기 pk 인지
    //        아기가 내 아기인지
    //        이거 말고 할 거 있나..? 없는듯.
    return memoryRepository.save(user, babyId, newMemory);
  }
}
