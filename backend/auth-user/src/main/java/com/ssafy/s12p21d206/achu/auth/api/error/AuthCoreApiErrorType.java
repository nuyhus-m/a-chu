package com.ssafy.s12p21d206.achu.auth.api.error;

import org.springframework.http.HttpStatus;

public enum AuthCoreApiErrorType {
  INTERNAL_SERVER_ERROR(
      AuthCoreApiErrorCode.AE1000,
      "서버 오류가 발생하였습니다.",
      AuthCoreApiErrorLevel.ERROR,
      HttpStatus.INTERNAL_SERVER_ERROR),
  NULL_PHONE_NUMBER(
      AuthCoreApiErrorCode.AE2000,
      "전화번호는 null일 수 없습니다.",
      AuthCoreApiErrorLevel.INFO,
      HttpStatus.BAD_REQUEST),
  INVALID_PHONE_NUMBER_FORMAT(
      AuthCoreApiErrorCode.AE2001,
      "유효하지 않은 전화번호 형식입니다.",
      AuthCoreApiErrorLevel.INFO,
      HttpStatus.BAD_REQUEST),
  NULL_VERIFICATION_PURPOSE(
      AuthCoreApiErrorCode.AE2002,
      "인증 목적은 null일 수 없습니다.",
      AuthCoreApiErrorLevel.INFO,
      HttpStatus.BAD_REQUEST),
  NULL_VERIFICATION_CODE(
      AuthCoreApiErrorCode.AE2003,
      "인증 코드는 null일 수 없습니다.",
      AuthCoreApiErrorLevel.INFO,
      HttpStatus.BAD_REQUEST),
  INVALID_VERIFICATION_CODE_FORMAT(
      AuthCoreApiErrorCode.AE2005,
      "유효하지 않은 인증 코드 형식입니다.",
      AuthCoreApiErrorLevel.INFO,
      HttpStatus.BAD_REQUEST),
  NULL_ID(
      AuthCoreApiErrorCode.AE2006,
      "id는 null일 수 없습니다.",
      AuthCoreApiErrorLevel.INFO,
      HttpStatus.BAD_REQUEST);

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
