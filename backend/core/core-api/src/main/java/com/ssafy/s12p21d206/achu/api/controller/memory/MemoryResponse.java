package com.ssafy.s12p21d206.achu.api.controller.memory;

import java.time.LocalDateTime;
import java.util.List;

public record MemoryResponse(
    Long id,
    String title,
    List<String> imgUrls,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
