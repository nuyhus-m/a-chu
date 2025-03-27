package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.validation.Password;
import com.ssafy.s12p21d206.achu.auth.api.controller.validation.Username;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ResetPasswordRequest(
    @NotNull @Username String username,
    @NotNull @Password String newPassword,
    @NotNull UUID verificationCodeId) {}
