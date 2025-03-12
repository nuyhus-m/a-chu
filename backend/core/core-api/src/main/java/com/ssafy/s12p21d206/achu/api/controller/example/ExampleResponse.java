package com.ssafy.s12p21d206.achu.api.controller.example;

import java.time.LocalDateTime;

public record ExampleResponse(
    Long id, String name, int age, LocalDateTime createdAt, LocalDateTime updatedAt) {}
