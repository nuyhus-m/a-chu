package com.ssafy.s12p21d206.achu.api.controller.memory;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MemoryController {

  @PostMapping("/babies/{babyId}/memories")
  public ApiResponse<DefaultIdResponse> appendMemory(
      @PathVariable String babyId,
      @RequestPart(name = "memoryImages") List<MultipartFile> memoryImages,
      @RequestPart(name = "request") AppendMemoryRequest request) {

    DefaultIdResponse response = new DefaultIdResponse(1L);
    return ApiResponse.success(response);
  }

  @GetMapping("/memories/{memoryId}")
  public ApiResponse<MemoryDetailResponse> findMemory(Long userId, @PathVariable Long memoryId) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 12, 14, 0);
    MemoryDetailResponse response = new MemoryDetailResponse(
        memoryId,
        "제목",
        "내용",
        List.of("https://image1.jpg", "https://image2.jpg"),
        createdAt,
        createdAt);
    return ApiResponse.success(response);
  }

  @GetMapping("/babies/{babyId}/memories")
  public ApiResponse<List<MemoryResponse>> findMemories(
      Long userId,
      @PathVariable Long babyId,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    LocalDateTime createdAt = LocalDateTime.of(2025, 3, 12, 14, 0);
    List<MemoryResponse> response = List.of(
        new MemoryResponse(
            1L, "제목1", List.of("https://image1.jpg", "https://image2.jpg"), createdAt, createdAt),
        new MemoryResponse(2L, "제목2", List.of("https://image3.jpg"), createdAt, createdAt));
    return ApiResponse.success(response);
  }

  @PatchMapping("/memories/{memoryId}/image")
  public ApiResponse<?> modifyMemoryImage(
      Long userId, @PathVariable Long memoryId, @RequestParam List<MultipartFile> memoryImages) {

    return ApiResponse.success();
  }

  @PatchMapping("/memories/{memoryId}")
  public ApiResponse<?> modifyMemory(
      Long userId, @PathVariable Long memoryId, @RequestBody ModifyMemoryRequest request) {

    return ApiResponse.success();
  }

  @DeleteMapping("/memories/{memoryId}")
  public ApiResponse<?> deleteMemory(Long userId, @PathVariable Long memoryId) {
    return ApiResponse.success();
  }
}
