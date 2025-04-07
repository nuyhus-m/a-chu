package com.ssafy.s12p21d206.achu.fcm.domain.error;

public enum FcmErrorType {
  DATA_NOT_FOUND(
      FcmErrorKind.CLIENT_ERROR, FcmErrorCode.FE1001, "데이터를 찾을 수 없습니다.", FcmErrorLevel.INFO);

  private final FcmErrorKind kind;
  private final FcmErrorCode code;
  private final String message;
  private final FcmErrorLevel level;

  FcmErrorType(FcmErrorKind kind, FcmErrorCode code, String message, FcmErrorLevel level) {
    this.kind = kind;
    this.code = code;
    this.message = message;
    this.level = level;
  }

  public FcmErrorKind getKind() {
    return kind;
  }

  public FcmErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public FcmErrorLevel getLevel() {
    return level;
  }
}
