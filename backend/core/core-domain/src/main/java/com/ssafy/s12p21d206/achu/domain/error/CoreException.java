package com.ssafy.s12p21d206.achu.domain.error;

public class CoreException extends RuntimeException {

  private final CoreErrorType errorType;

  public CoreException(CoreErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public CoreErrorType getErrorType() {
    return errorType;
  }
}
