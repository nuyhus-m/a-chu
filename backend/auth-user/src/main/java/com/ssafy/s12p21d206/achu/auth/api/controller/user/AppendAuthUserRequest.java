package com.ssafy.s12p21d206.achu.auth.api.controller.user;

public record AppendAuthUserRequest(
    String username,
    String password,
    String nickname,
    String phoneNumber,
    String phoneVerificationCode) {}
