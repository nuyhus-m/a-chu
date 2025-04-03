package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;

public record UserProfileResponse(Long id, String nickname, String profileImageUrl) {

  public static UserProfileResponse from(ChatUserProfile userProfile) {
    return new UserProfileResponse(
        userProfile.id(), userProfile.nickname(), userProfile.profileImageUrl());
  }
}
