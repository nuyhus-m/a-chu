package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class MemoryDeleter {
  private final MemoryRepository memoryRepository;
  private final BabyValidator babyValidator;

  public MemoryDeleter(MemoryRepository memoryRepository, BabyValidator babyValidator) {
    this.memoryRepository = memoryRepository;
    this.babyValidator = babyValidator;
  }

  public Long delete(User user, Long memoryId) {
    Memory memory = memoryRepository.findMemory(memoryId);
    babyValidator.validateParent(user, memory.babyId());
    return memoryRepository.delete(memoryId);
  }
}
