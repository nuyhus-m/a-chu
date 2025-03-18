package com.ssafy.s12p21d206.achu.api.controller.memory;

import java.time.LocalDateTime;
import java.util.List;

public record MemoryDetailResponse(
    Long id,
    String title,
    String content,
    List<String> imgUrls,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
