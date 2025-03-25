package com.ssafy.s12p21d206.achu.auth.api.controller.user;

import com.ssafy.s12p21d206.achu.auth.api.controller.validation.Nickname;
import jakarta.validation.constraints.NotNull;

public record ModifyNicknameRequest(@NotNull @Nickname String nickname) {}
