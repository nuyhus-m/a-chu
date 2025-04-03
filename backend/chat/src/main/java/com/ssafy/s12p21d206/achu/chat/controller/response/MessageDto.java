package com.ssafy.s12p21d206.achu.chat.controller.response;

import com.ssafy.s12p21d206.achu.chat.domain.ChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.time.LocalDateTime;

/**
 * 메시지 응답 DTO
 */
public record MessageDto(
    Long id,
    Long roomId,
    Long senderId,
    String senderNickname,
    String senderProfileImageUrl,
    String content,
    LocalDateTime timestamp,
    MessageType type) {

  /**
   * 메시지와 발신자 프로필로부터 메시지 DTO 생성
   */
  public static MessageDto fromMessage(ChatMessage message, ChatUserProfile senderProfile) {
    return new MessageDto(
        message.id(),
        message.roomId(),
        message.sender().id(),
        senderProfile.nickname(),
        senderProfile.profileImageUrl(),
        message.content(),
        message.timestamp(),
        message.type());
  }
}
