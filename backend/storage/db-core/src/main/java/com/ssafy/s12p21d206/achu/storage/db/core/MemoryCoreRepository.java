package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Memory;
import com.ssafy.s12p21d206.achu.domain.MemoryRepository;
import com.ssafy.s12p21d206.achu.domain.NewMemory;
import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryCoreRepository implements MemoryRepository {
  private final MemoryJpaRepository memoryJpaRepository;

  public MemoryCoreRepository(MemoryJpaRepository memoryJpaRepository) {
    this.memoryJpaRepository = memoryJpaRepository;
  }

  @Override
  public Memory save(User user, Long babyId, NewMemory newMemory) {
    return memoryJpaRepository
        .save(new MemoryEntity(newMemory.title(), newMemory.content(), newMemory.imgUrls(), babyId))
        .toMemory();
  }

  @Override
  public Memory findMemory(Long memoryId) {
    return memoryJpaRepository
        .findById(memoryId)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND))
        .toMemory();
  }
}
