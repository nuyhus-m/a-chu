package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.Baby;
import com.ssafy.s12p21d206.achu.domain.BabyService;
import com.ssafy.s12p21d206.achu.domain.NewBaby;
import com.ssafy.s12p21d206.achu.domain.support.SortType;
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

  private final BabyService babyService;

  public BabyController(BabyService babyService) {
    this.babyService = babyService;
  }

  @PostMapping("/babies")
  public ApiResponse<DefaultIdResponse> appendBaby(
      ApiUser apiUser,
      @RequestPart(name = "profileImage") MultipartFile profileImage,
      @RequestPart AppendBabyRequest request) {
    String imageUrl = "https://dummy-image-url.com/image.png";
    NewBaby newBaby = request.toNewBaby(imageUrl);
    Long id = babyService.append(apiUser.toUser(), newBaby);
    return ApiResponse.success(new DefaultIdResponse(id));
  }

  @GetMapping("/babies/{id}")
  public ApiResponse<BabyResponse> findBaby(Long userId, @PathVariable Long id) {
    Baby baby = babyService.findBaby(id);
    return ApiResponse.success(BabyResponse.from(baby));
  }

  @GetMapping("/babies")
  public ApiResponse<List<BabyResponse>> findBabies(
      ApiUser apiUser,
      @RequestParam Long offset,
      @RequestParam Long limit,
      @RequestParam SortType sort) {
    List<Baby> babies = babyService.findBabies(apiUser.toUser());
    return ApiResponse.success(BabyResponse.of(babies));
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

  @DeleteMapping("/babies/{id}")
  public ApiResponse<DefaultIdResponse> deleteBaby(Long userId, @PathVariable Long id) {
    babyService.delete(id);
    return ApiResponse.success(new DefaultIdResponse(id));
  }
}
