package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
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

  public List<Memory> readMemories(User user, Long babyId, Long offset, Long limit, SortType sort) {
    babyValidator.validateExists(babyId);
    babyValidator.validateParent(user, babyId);
    return memoryRepository.findMemories(babyId, offset, limit, sort);
  }
}
