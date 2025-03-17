package com.ssafy.s12p21d206.achu.api.controller.user;

import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import com.ssafy.s12p21d206.achu.api.response.IsUniqueResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

  @PostMapping("/users")
  public ApiResponse<DefaultIdResponse> appendUser(@RequestBody AppendUserRequest request) {
    DefaultIdResponse response = new DefaultIdResponse(1L);
    return ApiResponse.success(response);
  }

  @GetMapping("/users/{userId}")
  public ApiResponse<UserResponse> findUser(@PathVariable Long userId) {
    UserResponse response = new UserResponse(userId, "닉네임", "프로필이미지");
    return ApiResponse.success(response);
  }

  @GetMapping("/users/me")
  public ApiResponse<UserResponse> findMe(Long userId) {
    UserResponse response = new UserResponse(1L, "닉네임", "프로필이미지");
    return ApiResponse.success(response);
  }

  @GetMapping("/users/username/is-unique")
  public ApiResponse<IsUniqueResponse> checkUsernameIsUnique(@RequestParam String username) {
    IsUniqueResponse response = new IsUniqueResponse(true);
    return ApiResponse.success(response);
  }

  @GetMapping("/users/nickname/is-unique")
  public ApiResponse<IsUniqueResponse> checkNicknameIsUnique(@RequestParam String nickname) {
    IsUniqueResponse response = new IsUniqueResponse(true);
    return ApiResponse.success(response);
  }

  @PatchMapping("/users/nickname")
  public ApiResponse<?> modifyNickname(Long userId, @RequestBody ModifyNicknameRequest request) {
    return ApiResponse.success();
  }

  // Q. 프로필 이미지는 왜 처음부터 안받아?
  @PatchMapping("/users/profile-image")
  public ApiResponse<?> modifyProfileImage(
      Long userId, @RequestParam("profileImage") MultipartFile profileImage) {
    return ApiResponse.success();
  }

  @PatchMapping("/users/change-phone")
  public ApiResponse<?> modifyPhoneNumber(Long userId, @RequestBody ModifyPhoneRequest request) {
    return ApiResponse.success();
  }
}
