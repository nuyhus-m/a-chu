package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Service;

@Service
public class MemoryService {
  private final MemoryAppender memoryAppender;
  private final MemoryReader memoryReader;

  public private final MemoryService(MemoryAppender memoryAppender, MemoryReader memoryReader) {
    this.memoryAppender = memoryAppender;
    this.memoryReader = memoryReader;
  }

  public Long append(User user, Long babyId, NewMemory newMemory) {
    Memory memory = memoryAppender.append(user, babyId, newMemory);
    return memory.memoryId();
  }

  public Memory findMemory(User user, Long memoryId) {
    return memoryReader.readMemory(user, memoryId);
  }
}
