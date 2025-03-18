package com.ssafy.s12p21d206.achu.auth.domain.error;

public class AuthCoreException extends RuntimeException {

  private final AuthCoreErrorType errorType;

  public AuthCoreException(AuthCoreErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public AuthCoreErrorType getErrorType() {
    return errorType;
  }
}
