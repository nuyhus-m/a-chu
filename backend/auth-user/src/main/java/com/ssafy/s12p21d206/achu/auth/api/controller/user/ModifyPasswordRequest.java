package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.validation.Password;
import jakarta.validation.constraints.NotBlank;

public record ModifyPasswordRequest(
    @NotBlank String oldPassword, @NotBlank @Password String newPassword) {}
