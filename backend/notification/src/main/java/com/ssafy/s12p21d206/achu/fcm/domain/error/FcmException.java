package com.ssafy.s12p21d206.achu.fcm.domain.error;

public class FcmException extends RuntimeException {
  private final FcmErrorType errorType;

  public FcmException(FcmErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public FcmErrorType getErrorType() {
    return errorType;
  }
}
