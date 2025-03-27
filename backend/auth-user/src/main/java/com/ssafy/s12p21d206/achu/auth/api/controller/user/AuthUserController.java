package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.AuthApiUser;
import com.ssafy.s12p21d206.achu.auth.api.response.AuthApiResponse;
import com.ssafy.s12p21d206.achu.auth.api.response.AuthDefaultIdResponse;
import com.ssafy.s12p21d206.achu.auth.api.response.AuthIsUniqueResponse;
import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AuthUserController {

  private final AuthUserService authUserService;

  public AuthUserController(AuthUserService authUserService) {
    this.authUserService = authUserService;
  }

  @PostMapping("/users")
  public AuthApiResponse<AuthDefaultIdResponse> appendUser(
      @RequestBody @Validated AppendAuthUserRequest request) {
    AuthUser authUser =
        authUserService.appendAuthUser(request.toNewAuthUser(), request.verificationCodeId());
    AuthDefaultIdResponse response = new AuthDefaultIdResponse(authUser.id());
    return AuthApiResponse.success(response);
  }

  @PatchMapping("/users/password")
  public AuthApiResponse<Void> modifyPassword(
      AuthApiUser apiUser, @RequestBody @Validated ModifyPasswordRequest request) {
    authUserService.modifyPassword(apiUser.id(), request.oldPassword(), request.newPassword());
    return AuthApiResponse.success();
  }

  @PatchMapping("/users/password/reset")
  public AuthApiResponse<Void> resetPassword(@RequestBody @Validated ResetPasswordRequest request) {
    authUserService.resetPassword(
        request.username(), request.newPassword(), request.verificationCodeId());
    return AuthApiResponse.success();
  }

  @GetMapping("/users/me")
  public AuthApiResponse<AuthUserResponse> findMe(AuthApiUser authApiUser) {
    AuthUser authUser = authUserService.findUser(authApiUser.id());
    AuthUserResponse response = AuthUserResponse.from(authUser);
    return AuthApiResponse.success(response);
  }

  @GetMapping("/users/username/is-unique")
  public AuthApiResponse<AuthIsUniqueResponse> checkUsernameIsUnique(
      @RequestParam String username) {
    AuthIsUniqueResponse response =
        new AuthIsUniqueResponse(authUserService.isUsernameUnique(username));
    return AuthApiResponse.success(response);
  }

  @GetMapping("/users/nickname/is-unique")
  public AuthApiResponse<AuthIsUniqueResponse> checkNicknameIsUnique(
      @RequestParam String nickname) {
    AuthIsUniqueResponse response =
        new AuthIsUniqueResponse(authUserService.isNicknameUnique(nickname));
    return AuthApiResponse.success(response);
  }

  @PatchMapping("/users/nickname")
  public AuthApiResponse<Void> modifyNickname(
      AuthApiUser authApiUser, @RequestBody @Validated ModifyNicknameRequest request) {
    authUserService.modifyNickname(authApiUser.id(), request.nickname());
    return AuthApiResponse.success();
  }

  @PatchMapping("/users/profile-image")
  public AuthApiResponse<Void> modifyProfileImage(
      AuthApiUser authApiUser, @RequestParam("profileImage") MultipartFile profileImage) {
    return AuthApiResponse.success();
  }

  @PatchMapping("/users/phone")
  public AuthApiResponse<Void> modifyPhoneNumber(
      AuthApiUser authApiUser, @RequestBody ModifyPhoneRequest request) {
    authUserService.modifyPhoneNumber(
        authApiUser.id(), request.toPhone(), request.verificationCodeId());
    return AuthApiResponse.success();
  }
}
