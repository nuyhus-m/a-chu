package com.ssafy.s12p21d206.achu.api.controller.memory;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.api.controller.support.FileConverter;
import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.Memory;
import com.ssafy.s12p21d206.achu.domain.MemoryImageFacade;
import com.ssafy.s12p21d206.achu.domain.MemoryService;
import com.ssafy.s12p21d206.achu.domain.NewMemory;
import com.ssafy.s12p21d206.achu.domain.image.File;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;
import org.springframework.validation.annotation.Validated;
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

  private final MemoryService memoryService;
  private final MemoryImageFacade memoryImageFacade;

  public MemoryController(MemoryService memoryService, MemoryImageFacade memoryImageFacade) {
    this.memoryService = memoryService;
    this.memoryImageFacade = memoryImageFacade;
  }

  @PostMapping("/babies/{babyId}/memories")
  public ApiResponse<DefaultIdResponse> appendMemory(
      ApiUser apiUser,
      @PathVariable Long babyId,
      @RequestPart(name = "images") List<MultipartFile> multipartFiles,
      @RequestPart(name = "request") @Validated AppendMemoryRequest request) {
    List<String> imgUrls = List.of("goods1-img-url1.jpg", "goods1-img-url2.jpg");
    NewMemory newMemory = request.toNewMemory();
    List<File> imageFiles = multipartFiles.stream().map(FileConverter::convert).toList();
    Memory memory = memoryImageFacade.append(apiUser.toUser(), babyId, newMemory, imageFiles);
    return ApiResponse.success(new DefaultIdResponse(memory.memoryId()));
  }

  @GetMapping("/memories/{memoryId}")
  public ApiResponse<MemoryDetailResponse> findMemory(
      ApiUser apiUser, @PathVariable Long memoryId) {
    Memory memory = memoryService.findMemory(apiUser.toUser(), memoryId);
    MemoryDetailResponse response = MemoryDetailResponse.from(memory);
    return ApiResponse.success(response);
  }

  @GetMapping("/babies/{babyId}/memories")
  public ApiResponse<List<MemoryResponse>> findMemories(
      ApiUser apiUser,
      @PathVariable Long babyId,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    List<Memory> memories =
        memoryService.findMemories(apiUser.toUser(), babyId, offset, limit, sort);
    List<MemoryResponse> responses = MemoryResponse.of(memories);
    return ApiResponse.success(responses);
  }

  @PatchMapping("/memories/{memoryId}/image")
  public ApiResponse<Void> modifyMemoryImage(
      ApiUser apiUser,
      @PathVariable Long memoryId,
      @RequestPart(name = "memoryImages") List<MultipartFile> multipartFiles) {
    List<File> imageFiles = multipartFiles.stream().map(FileConverter::convert).toList();
    memoryImageFacade.modifyImages(apiUser.toUser(), memoryId, imageFiles);
    return ApiResponse.success();
  }

  @PatchMapping("/memories/{memoryId}")
  public ApiResponse<Void> modifyMemory(
      ApiUser apiUser,
      @PathVariable Long memoryId,
      @RequestBody @Validated ModifyMemoryRequest request) {
    memoryService.modifyMemory(apiUser.toUser(), memoryId, request.toModifyMemory());
    return ApiResponse.success();
  }

  @DeleteMapping("/memories/{memoryId}")
  public ApiResponse<DefaultIdResponse> deleteMemory(ApiUser apiUser, @PathVariable Long memoryId) {
    memoryService.delete(apiUser.toUser(), memoryId);
    return ApiResponse.success(new DefaultIdResponse(memoryId));
  }
}
