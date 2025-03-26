package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.api.validation.BabyNickname;
import jakarta.validation.constraints.NotNull;

public record ModifyBabyNicknameRequest(@NotNull @BabyNickname String nickname) {}
