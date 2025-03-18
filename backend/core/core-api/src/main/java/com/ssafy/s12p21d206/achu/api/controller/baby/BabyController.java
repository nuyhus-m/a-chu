package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.support.Sex;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BabyController {

  @PostMapping("/babies")
  public ApiResponse<DefaultIdResponse> appendBaby(
      @RequestPart(name = "profileImage") MultipartFile profileImage,
      @RequestPart AppendBabyRequest request) {

    DefaultIdResponse response = new DefaultIdResponse(1L);
    return ApiResponse.success(response);
  }

  @GetMapping("/babies/{babyId}")
  public ApiResponse<BabyDetailResponse> findBaby(Long userId, @PathVariable Long babyId) {

    BabyDetailResponse response = new BabyDetailResponse(
        babyId, "강두식", Sex.FEMALE, "https://test-image.png", LocalDate.of(2025, 3, 20));
    return ApiResponse.success(response);
  }

  @GetMapping("/babies")
  public ApiResponse<List<BabyResponse>> findBabies(
      Long userId,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {

    LocalDate birth1 = LocalDate.of(2025, 3, 20);
    LocalDate birth2 = LocalDate.of(2026, 5, 5);

    List<BabyResponse> response = List.of(
        new BabyResponse(1L, "강두식", "https://test-image1.png", birth1),
        new BabyResponse(2L, "강삼식", "https://test-image2.png", birth2));

    return ApiResponse.success(response);
  }

  @PatchMapping("/babies/{babyId}/profile-image")
  public ApiResponse<Void> modifyProfileImage(
      Long userId, @PathVariable Long babyId, @RequestParam MultipartFile profileImage) {

    return ApiResponse.success();
  }

  @PutMapping("/babies/{babyId}")
  public ApiResponse<Void> modifyBaby(
      Long userId, @PathVariable Long babyId, @RequestBody ModifyBabyRequest request) {

    return ApiResponse.success();
  }

  @DeleteMapping("/babies/{babyId}")
  public ApiResponse<Void> deleteBaby(Long userId, @PathVariable Long babyId) {
    return ApiResponse.success();
  }
}
