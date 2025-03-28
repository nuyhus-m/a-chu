package com.ssafy.s12p21d206.achu.api.controller.memory;

import com.ssafy.s12p21d206.achu.domain.Memory;
import java.time.LocalDateTime;
import java.util.List;

public record MemoryResponse(
    Long id, String title, String imgUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
  public static List<MemoryResponse> of(List<Memory> memories) {
    return memories.stream()
        .map(memory -> new MemoryResponse(
            memory.memoryId(),
            memory.title(),
            memory.imgUrls().get(0),
            memory.defaultDateTime().createdAt(),
            memory.defaultDateTime().updatedAt()))
        .toList();
  }
}
