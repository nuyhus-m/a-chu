package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.api.response.AuthApiResponse;
import com.ssafy.s12p21d206.achu.auth.domain.verification.PhoneVerificationService;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationCode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneVerificationController {

  private final PhoneVerificationService phoneVerificationService;

  public PhoneVerificationController(PhoneVerificationService phoneVerificationService) {
    this.phoneVerificationService = phoneVerificationService;
  }

  @PostMapping("/verification/request")
  public AuthApiResponse<PhoneVerificationResponse> createPhoneVerificationCode(
      @RequestBody @Validated PhoneVerificationRequest request) {
    VerificationCode phoneVerificationCode = phoneVerificationService.issuePhoneVerificationCode(
        request.toPhoneNumber(), request.toVerificationPurpose());
    return AuthApiResponse.success(PhoneVerificationResponse.of(phoneVerificationCode));
  }

  @PostMapping("/verification/verify")
  public AuthApiResponse<Void> validatePhoneVerificationCode(
      @RequestBody @Validated VerifyVerificationCodeRequest request) {
    phoneVerificationService.verifyPhoneVerificationCode(request.id(), request.code());
    return AuthApiResponse.success();
  }
}
