package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Service;

@Service
public class MemoryService {
  private final MemoryAppender memoryAppender;

  public MemoryService(MemoryAppender memoryAppender) {
    this.memoryAppender = memoryAppender;
  }

  public Long append(User user, Long babyId, NewMemory newMemory) {
    Memory memory = memoryAppender.append(user, babyId, newMemory);
    return memory.memoryId();
  }
}
