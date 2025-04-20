package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.validation.PhoneNumber;
import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ModifyPhoneRequest(
    @NotNull UUID verificationCodeId, @NotNull @PhoneNumber String phoneNumber) {

  public Phone toPhone() {
    return new Phone(phoneNumber);
  }
}
