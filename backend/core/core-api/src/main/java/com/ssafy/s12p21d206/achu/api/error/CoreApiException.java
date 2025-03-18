package com.ssafy.s12p21d206.achu.api.error;

public class CoreApiException extends RuntimeException {
  private final CoreApiErrorType errorType;

  public CoreApiException(CoreApiErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public CoreApiErrorType getErrorType() {
    return errorType;
  }
}
