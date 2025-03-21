package com.ssafy.s12p21d206.achu.auth.domain;

import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;
import java.time.Duration;
import java.util.UUID;

public record VerificationCode(
    UUID id,
    Phone phone,
    String code,
    VerificationPurpose purpose,
    boolean isVerified,
    Duration expiresIn,
    AuthDefaultDateTime defaultDateTime) {}
