package com.ssafy.s12p21d206.achu.api.controller.user;

public record AppendUserRequest(
    String username, String password, String nickname, String phoneNumber) {}
