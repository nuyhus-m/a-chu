package com.ssafy.s12p21d206.achu.auth.domain.error;

public enum AuthCoreErrorType {
  ;

  private final AuthCoreErrorKind kind;
  private final AuthCoreErrorCode code;
  private final String message;
  private final AuthCoreErrorLevel level;

  AuthCoreErrorType(
      AuthCoreErrorKind kind, AuthCoreErrorCode code, String message, AuthCoreErrorLevel level) {
    this.kind = kind;
    this.code = code;
    this.message = message;
    this.level = level;
  }

  public AuthCoreErrorKind getKind() {
    return kind;
  }

  public AuthCoreErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public AuthCoreErrorLevel getLevel() {
    return level;
  }
}
