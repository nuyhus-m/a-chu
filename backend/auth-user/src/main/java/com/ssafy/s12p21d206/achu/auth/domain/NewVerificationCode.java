package com.ssafy.s12p21d206.achu.auth.domain;

import java.time.Duration;

public record NewVerificationCode(String code, Duration expiresIn) {}
