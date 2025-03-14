package com.ssafy.s12p21d206.achu.api.controller.user;

import java.time.LocalDateTime;

public record UserResponse(
    Long userId,
    String username,
    String nickname,
    String imgUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
