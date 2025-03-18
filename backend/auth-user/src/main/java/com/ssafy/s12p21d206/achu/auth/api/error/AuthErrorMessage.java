package com.ssafy.s12p21d206.achu.auth.api.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;

public class AuthErrorMessage {

  private final String code;

  private final String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final Object data;

  public AuthErrorMessage(AuthCoreErrorType errorType) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
    this.data = null;
  }

  public AuthErrorMessage(AuthCoreErrorType errorType, Object data) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
    this.data = data;
  }

  public AuthErrorMessage(AuthCoreApiErrorType errorType) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
    this.data = null;
  }

  public AuthErrorMessage(AuthCoreApiErrorType errorType, Object data) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Object getData() {
    return data;
  }
}
