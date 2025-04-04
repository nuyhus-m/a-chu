package com.ssafy.s12p21d206.achu.chat.domain.user;

/**
 * 채팅 사용자 프로필 정보
 */
public record ChatUserProfile(Long id, String nickname, String profileImageUrl) {
  public static ChatUserProfile of(Long id, String nickname, String profileImageUrl) {
    return new ChatUserProfile(id, nickname, profileImageUrl);
  }

  public static ChatUserProfile from(ChatUser chatUser, String nickname, String profileImageUrl) {
    return new ChatUserProfile(chatUser.id(), nickname, profileImageUrl);
  }
}
