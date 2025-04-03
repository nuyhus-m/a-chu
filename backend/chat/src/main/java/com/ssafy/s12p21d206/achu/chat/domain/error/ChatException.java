package com.ssafy.s12p21d206.achu.chat.domain.error;

public class ChatException extends RuntimeException {

  private final ChatErrorType errorType;

  public ChatException(ChatErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public ChatException(ChatErrorType errorType, String customMessage) {
    super(customMessage);
    this.errorType = errorType;
  }

  public ChatException(ChatErrorType errorType, Throwable cause) {
    super(errorType.getMessage(), cause);
    this.errorType = errorType;
  }

  public ChatException(ChatErrorType errorType, String customMessage, Throwable cause) {
    super(customMessage, cause);
    this.errorType = errorType;
  }

  public ChatErrorType getErrorType() {
    return errorType;
  }
}
