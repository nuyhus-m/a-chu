package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.api.response.AuthApiResponse;
import com.ssafy.s12p21d206.achu.auth.domain.PhoneVerificationService;
import com.ssafy.s12p21d206.achu.auth.domain.VerificationCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneVerificationController {

  private final PhoneVerificationService phoneVerificationService;

  public PhoneVerificationController(PhoneVerificationService phoneVerificationService) {
    this.phoneVerificationService = phoneVerificationService;
  }

  @PostMapping("/users/phone/phone-verification")
  public AuthApiResponse<PhoneVerificationResponse> createPhoneVerificationCode(
      @RequestBody PhoneVerificationRequest request) {
    VerificationCode phoneVerificationCode = phoneVerificationService.issuePhoneVerificationCode(
        request.toPhoneNumber(), request.toVerificationPurpose());
    return AuthApiResponse.success(PhoneVerificationResponse.of(phoneVerificationCode));
  }
}
