package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.time.LocalDateTime;

/**
 * 새로운 채팅 메시지를 생성할 때 사용하는 클래스
 * ID 값이 없는 상태를 명확하게 표현하기 위한 목적
 */
public record NewChatMessage(
    Long roomId, ChatUser sender, String content, LocalDateTime timestamp, MessageType type) {

  /**
   * 일반 채팅 메시지 생성
   */
  public static NewChatMessage chat(Long roomId, ChatUser sender, String content) {
    return new NewChatMessage(roomId, sender, content, LocalDateTime.now(), MessageType.CHAT);
  }

  /**
   * 퇴장 메시지 생성
   */
  public static NewChatMessage leave(Long roomId, ChatUser user) {
    return new NewChatMessage(
        roomId, user, user.id() + "님이 퇴장하셨습니다.", LocalDateTime.now(), MessageType.LEAVE);
  }

  /**
   * 입장 메시지 생성
   *
   * @deprecated 더 이상 입장 메시지를 사용하지 않음, 하위 호환성을 위해 유지
   */
  @Deprecated
  public static NewChatMessage join(Long roomId, ChatUser user) {
    return new NewChatMessage(
        roomId, user, user.id() + "님이 입장하셨습니다.", LocalDateTime.now(), MessageType.CHAT);
  }
}
