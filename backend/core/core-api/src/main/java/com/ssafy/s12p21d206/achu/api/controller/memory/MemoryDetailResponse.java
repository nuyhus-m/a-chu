package com.ssafy.s12p21d206.achu.api.controller.memory;

import com.ssafy.s12p21d206.achu.domain.Memory;
import java.time.LocalDateTime;
import java.util.List;

public record MemoryDetailResponse(
    Long id,
    String title,
    String content,
    List<String> imgUrls,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
  public static MemoryDetailResponse from(Memory memory) {
    return new MemoryDetailResponse(
        memory.memoryId(),
        memory.title(),
        memory.content(),
        memory.imgUrls(),
        memory.createdAt(),
        memory.updatedAt());
  }
}
