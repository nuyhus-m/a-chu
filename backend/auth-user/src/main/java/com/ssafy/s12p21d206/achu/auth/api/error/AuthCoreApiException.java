package com.ssafy.s12p21d206.achu.auth.api.error;

public class AuthCoreApiException extends RuntimeException {
  private final AuthCoreApiErrorType errorType;

  public AuthCoreApiException(AuthCoreApiErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public AuthCoreApiErrorType getErrorType() {
    return errorType;
  }
}
