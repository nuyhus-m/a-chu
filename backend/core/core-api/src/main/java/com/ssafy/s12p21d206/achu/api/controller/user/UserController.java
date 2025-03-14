package com.ssafy.s12p21d206.achu.api.controller.user;

import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.api.response.DefaultIdResponse;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

  @PostMapping("/users")
  public ApiResponse<DefaultIdResponse> appendUser(@RequestBody AppendUserRequest request) {
    DefaultIdResponse response = new DefaultIdResponse(1L);
    return ApiResponse.success(response);
  }

  @GetMapping("/users/{userId}")
  public ApiResponse<UserResponse> findUser(@PathVariable Long userId) {
    LocalDateTime createdAt = LocalDateTime.of(2023, 3, 13, 15, 0);
    UserResponse response = new UserResponse(userId, "아이디", "닉네임", "프로필이미지", createdAt, createdAt);
    return ApiResponse.success(response);
  }

  @GetMapping("/users/username/is-unique")
  public ApiResponse<?> checkUsernameIsUnique(@RequestParam String username) {
    return ApiResponse.success();
  }

  @GetMapping("/users/nickname/is-unique")
  public ApiResponse<?> checkNicknameIsUnique(@RequestParam String nickname) {
    return ApiResponse.success();
  }

  // Q. 뭐 반환해야 하나요? 변경된 닉네임을 반환해야 할까요?
  @PatchMapping("/users/{userId}/nickname")
  public ApiResponse<?> modifyNickname(
      @PathVariable Long userId, @RequestBody ModifyNicknameRequest request) {
    return ApiResponse.success();
  }

  //  @PatchMapping("/users/{userId}/profileImage")
  //  public ApiResponse<?> uploadProfileImage(
  //          @PathVariable Long userId,
  //          @RequestParam("profileImg")
  //  )

}
