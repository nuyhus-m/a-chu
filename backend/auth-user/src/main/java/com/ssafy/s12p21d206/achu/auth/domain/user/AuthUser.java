package com.ssafy.s12p21d206.achu.auth.domain.user;

import com.ssafy.s12p21d206.achu.auth.domain.Phone;
import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;

public record AuthUser(
    Long id,
    String username,
    String password,
    String nickname,
    Phone phone,
    AuthDefaultDateTime defaultDateTime) {}
