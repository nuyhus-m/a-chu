package com.ssafy.s12p21d206.achu.domain.error;

public enum CoreErrorType {
  DATA_NOT_FOUND(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1000, "데이터를 찾을 수 없습니다.", CoreErrorLevel.INFO),
  CANNOT_ACCESS_BABY(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1001, "해당 아기에 접근 권한이 없습니다.", CoreErrorLevel.INFO),
  CANNOT_REGISTER_MORE_BABIES(
      CoreErrorKind.CLIENT_ERROR,
      CoreErrorCode.E1002,
      "최대 15명의 아기까지만 등록할 수 있습니다.",
      CoreErrorLevel.WARN);

  private final CoreErrorKind kind;
  private final CoreErrorCode code;
  private final String message;
  private final CoreErrorLevel level;

  CoreErrorType(CoreErrorKind kind, CoreErrorCode code, String message, CoreErrorLevel level) {
    this.kind = kind;
    this.code = code;
    this.message = message;
    this.level = level;
  }

  public CoreErrorKind getKind() {
    return kind;
  }

  public CoreErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public CoreErrorLevel getLevel() {
    return level;
  }
}
