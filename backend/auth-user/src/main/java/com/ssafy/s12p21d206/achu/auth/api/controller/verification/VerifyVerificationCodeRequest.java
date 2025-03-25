package com.ssafy.s12p21d206.achu.auth.api.controller.verification;

import com.ssafy.s12p21d206.achu.auth.api.controller.validation.VerificationCode;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record VerifyVerificationCodeRequest(
    @NotNull UUID id, @NotNull @VerificationCode String code) {}
