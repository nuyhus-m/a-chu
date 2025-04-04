package com.ssafy.s12p21d206.achu.chat.domain.message;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;

public record UserProfileDto(Long id, String nickname, String profileImageUrl) {

  public static UserProfileDto from(ChatUserProfile userProfile) {
    return new UserProfileDto(
        userProfile.id(), userProfile.nickname(), userProfile.profileImageUrl());
  }
}
