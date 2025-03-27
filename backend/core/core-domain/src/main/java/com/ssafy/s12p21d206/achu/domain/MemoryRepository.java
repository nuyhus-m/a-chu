package com.ssafy.s12p21d206.achu.domain;

public interface MemoryRepository {
  Memory save(User user, Long babyId, NewMemory newMemory);
}
