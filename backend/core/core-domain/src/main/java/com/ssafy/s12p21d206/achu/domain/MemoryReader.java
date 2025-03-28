package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MemoryReader {
  private final MemoryRepository memoryRepository;
  private final MemoryValidator memoryValidator;
  private final BabyValidator babyValidator;

  public MemoryReader(
      MemoryRepository memoryRepository,
      MemoryValidator memoryValidator,
      BabyValidator babyValidator) {
    this.memoryRepository = memoryRepository;
    this.memoryValidator = memoryValidator;
    this.babyValidator = babyValidator;
  }

  public Memory readMemory(User user, Long memoryId) {
    memoryValidator.validateExists(memoryId);
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
