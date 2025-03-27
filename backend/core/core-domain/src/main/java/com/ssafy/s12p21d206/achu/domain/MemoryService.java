package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MemoryService {
  private final MemoryAppender memoryAppender;
  private final MemoryReader memoryReader;
  private final MemoryModifier memoryModifier;
  private final MemoryDeleter memoryDeleter;

  public MemoryService(
      MemoryAppender memoryAppender,
      MemoryReader memoryReader,
      MemoryModifier memoryModifier,
      MemoryDeleter memoryDeleter) {
    this.memoryAppender = memoryAppender;
    this.memoryReader = memoryReader;
    this.memoryModifier = memoryModifier;
    this.memoryDeleter = memoryDeleter;
  }

  public Long append(User user, Long babyId, NewMemory newMemory) {
    Memory memory = memoryAppender.append(user, babyId, newMemory);
    return memory.memoryId();
  }

  public Memory findMemory(User user, Long memoryId) {
    return memoryReader.readMemory(user, memoryId);
  }

  public List<Memory> findMemories(User user, Long babyId, Long offset, Long limit, SortType sort) {
    return memoryReader.readMemories(user, babyId, offset, limit, sort);
  }

  public Memory modifyMemory(User user, Long memoryId, ModifyMemory modifyMemory) {
    return memoryModifier.modifyMemory(user, memoryId, modifyMemory);
  }

  public Long delete(User user, Long memoryId) {
    return memoryDeleter.delete(user, memoryId);
  }
}
