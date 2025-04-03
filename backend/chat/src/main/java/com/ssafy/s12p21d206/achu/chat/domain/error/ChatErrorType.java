package com.ssafy.s12p21d206.achu.chat.domain.error;

public enum ChatErrorType {
  // 채팅방 관련 에러 (CE1xxx)
  CHAT_ROOM_NOT_FOUND(
      ChatErrorKind.CLIENT_ERROR, ChatErrorCode.CE1001, "채팅방을 찾을 수 없습니다.", ChatErrorLevel.ERROR),
  CHAT_ROOM_ALREADY_EXISTS(
      ChatErrorKind.CLIENT_ERROR, ChatErrorCode.CE1002, "이미 존재하는 채팅방입니다.", ChatErrorLevel.INFO),

  // 참여자 관련 에러 (CE2xxx)
  USER_NOT_IN_CHAT_ROOM(
      ChatErrorKind.CLIENT_ERROR, ChatErrorCode.CE2001, "채팅방에 참여하고 있지 않습니다.", ChatErrorLevel.ERROR),
  USER_ALREADY_LEFT_CHAT_ROOM(
      ChatErrorKind.CLIENT_ERROR, ChatErrorCode.CE2002, "이미 채팅방을 나간 상태입니다.", ChatErrorLevel.INFO),
  CHAT_PARTNER_NOT_FOUND(
      ChatErrorKind.CLIENT_ERROR, ChatErrorCode.CE2003, "채팅방에 상대방이 없습니다.", ChatErrorLevel.ERROR),

  // 메시지 관련 에러 (CE3xxx)
  MESSAGE_NOT_FOUND(
      ChatErrorKind.CLIENT_ERROR, ChatErrorCode.CE3001, "메시지를 찾을 수 없습니다.", ChatErrorLevel.ERROR),
  EMPTY_MESSAGE_CONTENT(
      ChatErrorKind.CLIENT_ERROR, ChatErrorCode.CE3002, "메시지 내용이 비어있습니다.", ChatErrorLevel.WARN),
  MESSAGE_NOT_IN_CHAT_ROOM(
      ChatErrorKind.CLIENT_ERROR,
      ChatErrorCode.CE3003,
      "메시지가 채팅방에 속하지 않습니다.",
      ChatErrorLevel.ERROR),

  // 사용자 관련 에러 (CE4xxx)
  USER_PROFILE_NOT_FOUND(
      ChatErrorKind.CLIENT_ERROR,
      ChatErrorCode.CE4001,
      "사용자 프로필을 찾을 수 없습니다.",
      ChatErrorLevel.ERROR),

  // 서버 에러 (CE5xxx)
  INTERNAL_SERVER_ERROR(
      ChatErrorKind.SERVER_ERROR, ChatErrorCode.CE5001, "서버 내부 오류가 발생했습니다.", ChatErrorLevel.ERROR);

  private final ChatErrorKind kind;
  private final ChatErrorCode code;
  private final String message;
  private final ChatErrorLevel level;

  ChatErrorType(ChatErrorKind kind, ChatErrorCode code, String message, ChatErrorLevel level) {
    this.kind = kind;
    this.code = code;
    this.message = message;
    this.level = level;
  }

  public ChatErrorKind getKind() {
    return kind;
  }

  public ChatErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public ChatErrorLevel getLevel() {
    return level;
  }
}
