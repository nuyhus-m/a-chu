package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;

public interface MemoryRepository {
  Memory save(Long babyId, NewMemory newMemory);

  Memory findMemory(Long memoryId);

  List<Memory> findMemories(Long babyId, Long offset, Long limit, SortType sort);

  Memory modifyMemory(Long memoryId, ModifyMemory modifyMemory);

  Long delete(Long memoryId);

  boolean existsById(Long memoryId);
}
