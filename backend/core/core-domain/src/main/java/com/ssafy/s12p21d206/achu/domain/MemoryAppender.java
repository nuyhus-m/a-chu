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

  public Memory append(User user, Long babyId, NewMemory newMemory) {
    babyValidator.validateExists(babyId);
    babyValidator.validateParent(user, babyId);
    return memoryRepository.save(user, babyId, newMemory);
  }
}
