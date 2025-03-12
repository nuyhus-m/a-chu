package com.ssafy.s12p21d206.achu.api.controller.example;

import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

  @PostMapping("/examples")
  public ApiResponse<DefaultIdResponse> appendExample(@RequestBody AppendExampleRequest request) {
    DefaultIdResponse response = new DefaultIdResponse(1L);
    return ApiResponse.success(response);
  }

  @GetMapping("/examples/{exampleId}")
  public ApiResponse<ExampleResponse> findExample(Long userId, @PathVariable Long exampleId) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 11, 20, 0);
    ExampleResponse response = new ExampleResponse(exampleId, "이름", 20, createdAt, createdAt);
    return ApiResponse.success(response);
  }

  @GetMapping("/examples")
  public ApiResponse<List<ExampleResponse>> findExamples(
      Long userId,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 11, 20, 0);
    List<ExampleResponse> response = List.of(
        new ExampleResponse(1L, "이름", 20, createdAt, createdAt),
        new ExampleResponse(2L, "이름2", 21, createdAt, createdAt.plusHours(1L)));
    return ApiResponse.success(response);
  }

  @PatchMapping("/examples/{exampleId}/name")
  public ApiResponse<ExampleResponse> modifyName(
      Long userId, @PathVariable Long exampleId, @RequestBody ModifyExampleNameRequest request) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 11, 20, 0);
    ExampleResponse response =
        new ExampleResponse(exampleId, request.name(), 20, createdAt, createdAt);
    return ApiResponse.success(response);
  }

  @DeleteMapping("/examples/{exampleId}")
  public ApiResponse<?> deleteExample(Long userId, @PathVariable Long exampleId) {
    return ApiResponse.success();
  }
}
