package com.ssafy.s12p21d206.achu.auth.api.error;

import org.springframework.http.HttpStatus;

public enum AuthCoreApiErrorType {
  INTERNAL_SERVER_ERROR(
      AuthCoreApiErrorCode.AE1000,
      "서버 오류가 발생하였습니다.",
      AuthCoreApiErrorLevel.ERROR,
      HttpStatus.INTERNAL_SERVER_ERROR),
  ;

  private final AuthCoreApiErrorCode code;
  private final String message;
  private final AuthCoreApiErrorLevel level;
  private final HttpStatus status;

  AuthCoreApiErrorType(
      AuthCoreApiErrorCode code, String message, AuthCoreApiErrorLevel level, HttpStatus status) {
    this.code = code;
    this.message = message;
    this.level = level;
    this.status = status;
  }

  public AuthCoreApiErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public AuthCoreApiErrorLevel getLevel() {
    return level;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
