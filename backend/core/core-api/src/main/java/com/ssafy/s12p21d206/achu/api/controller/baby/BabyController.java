package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.api.controller.support.FileConverter;
import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.domain.Baby;
import com.ssafy.s12p21d206.achu.domain.BabyImageFacade;
import com.ssafy.s12p21d206.achu.domain.BabyService;
import com.ssafy.s12p21d206.achu.domain.NewBaby;
import com.ssafy.s12p21d206.achu.domain.image.File;
import java.util.List;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BabyController {

  private final BabyService babyService;
  private final BabyImageFacade babyImageFacade;

  public BabyController(BabyService babyService, BabyImageFacade babyImageFacade) {
    this.babyService = babyService;
    this.babyImageFacade = babyImageFacade;
  }

  @PostMapping("/babies")
  public ApiResponse<DefaultIdResponse> appendBaby(
      ApiUser apiUser,
      @RequestPart(name = "profileImage", required = false) MultipartFile profileImage,
      @RequestPart @Validated AppendBabyRequest request) {
    NewBaby newBaby = request.toNewBaby();

    if (Objects.isNull(profileImage)) {
      Long id = babyImageFacade.append(apiUser.toUser(), newBaby);
      return ApiResponse.success(new DefaultIdResponse(id));
    }

    File imageFile = FileConverter.convert(profileImage);
    Long id = babyImageFacade.append(apiUser.toUser(), imageFile, newBaby);
    return ApiResponse.success(new DefaultIdResponse(id));
  }

  @GetMapping("/babies/{id}")
  public ApiResponse<BabyResponse> findBaby(ApiUser apiUser, @PathVariable Long id) {
    Baby baby = babyService.findBaby(apiUser.toUser(), id);
    return ApiResponse.success(BabyResponse.from(baby));
  }

  @GetMapping("/babies")
  public ApiResponse<List<BabyResponse>> findBabies(ApiUser apiUser) {
    List<Baby> babies = babyService.findBabies(apiUser.toUser());
    return ApiResponse.success(BabyResponse.of(babies));
  }

  @PatchMapping("/babies/{babyId}/profile-image")
  public ApiResponse<Void> modifyProfileImage(
      ApiUser apiUser, @PathVariable Long babyId, @RequestParam MultipartFile profileImage) {
    File imageFile = FileConverter.convert(profileImage);
    babyImageFacade.modifyImageUrl(apiUser.toUser(), babyId, imageFile);
    return ApiResponse.success();
  }

  @PatchMapping("/babies/{id}/nickname")
  public ApiResponse<Void> modifyBabyNickname(
      ApiUser apiUser,
      @PathVariable Long id,
      @RequestBody @Validated ModifyBabyNicknameRequest request) {
    babyService.modifyNickname(apiUser.toUser(), id, request.nickname());
    return ApiResponse.success();
  }

  @PatchMapping("/babies/{id}/birth")
  public ApiResponse<Void> modifyBabyBirth(
      ApiUser apiUser, @PathVariable Long id, @RequestBody ModifyBabyBirthRequest request) {
    babyService.modifyBirth(apiUser.toUser(), id, request.birth());
    return ApiResponse.success();
  }

  @PatchMapping("/babies/{id}/gender")
  public ApiResponse<Void> modifyBabyGender(
      ApiUser apiUser, @PathVariable Long id, @RequestBody ModifyBabyGenderRequest request) {
    babyService.modifyGender(apiUser.toUser(), id, request.gender());
    return ApiResponse.success();
  }

  @DeleteMapping("/babies/{id}")
  public ApiResponse<DefaultIdResponse> deleteBaby(ApiUser apiUser, @PathVariable Long id) {
    babyService.delete(apiUser.toUser(), id);
    return ApiResponse.success(new DefaultIdResponse(id));
  }
}
