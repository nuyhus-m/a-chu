package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class MemoryValidator {
  private final MemoryRepository memoryRepository;

  public MemoryValidator(MemoryRepository memoryRepository) {
    this.memoryRepository = memoryRepository;
  }

  public void validateExists(Long memoryId) {
    if (!memoryRepository.existsById(memoryId)) {
      throw new CoreException(CoreErrorType.DATA_NOT_FOUND);
    }
  }
}
