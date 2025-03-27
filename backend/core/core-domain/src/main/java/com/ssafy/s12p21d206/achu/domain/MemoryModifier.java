package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class MemoryModifier {

  private final MemoryRepository memoryRepository;
  private final BabyValidator babyValidator;

  public MemoryModifier(MemoryRepository memoryRepository, BabyValidator babyValidator) {
    this.memoryRepository = memoryRepository;
    this.babyValidator = babyValidator;
  }

  public Memory modifyMemory(User user, Long memoryId, ModifyMemory modifyMemory) {
    Memory memory = memoryRepository.findMemory(memoryId);
    babyValidator.validateParent(user, memory.babyId());
    return memoryRepository.modifyMemory(memoryId, modifyMemory);
  }
}
