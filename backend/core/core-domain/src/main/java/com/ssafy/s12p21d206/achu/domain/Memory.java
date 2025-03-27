package com.ssafy.s12p21d206.achu.domain;

import java.time.LocalDateTime;
import java.util.List;

public record Memory(
    Long memoryId,
    String title,
    String content,
    List<String> imgUrls,
    Long babyId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
