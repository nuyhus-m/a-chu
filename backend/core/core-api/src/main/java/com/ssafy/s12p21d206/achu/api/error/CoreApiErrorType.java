package com.ssafy.s12p21d206.achu.api.error;

import org.springframework.http.HttpStatus;

public enum CoreApiErrorType {
  INTERNAL_SERVER_ERROR(
      CoreApiErrorCode.AE1000,
      "서버 오류가 발생하였습니다.",
      CoreApiErrorLevel.ERROR,
      HttpStatus.INTERNAL_SERVER_ERROR),
  ;

  private final CoreApiErrorCode code;
  private final String message;
  private final CoreApiErrorLevel level;
  private final HttpStatus status;

  CoreApiErrorType(
      CoreApiErrorCode code, String message, CoreApiErrorLevel level, HttpStatus status) {
    this.code = code;
    this.message = message;
    this.level = level;
    this.status = status;
  }

  public CoreApiErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public CoreApiErrorLevel getLevel() {
    return level;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
