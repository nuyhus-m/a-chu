package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class MemoryReader {
  private final MemoryRepository memoryRepository;
  private final BabyValidator babyValidator;

  public MemoryReader(MemoryRepository memoryRepository, BabyValidator babyValidator) {
    this.memoryRepository = memoryRepository;
    this.babyValidator = babyValidator;
  }

  public Memory readMemory(User user, Long memoryId) {
    Memory memory = memoryRepository.findMemory(memoryId);
    babyValidator.validateParent(user, memory.babyId());
    return memory;
  }
}
