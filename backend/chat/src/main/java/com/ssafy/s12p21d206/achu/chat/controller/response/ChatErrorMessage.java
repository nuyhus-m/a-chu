package com.ssafy.s12p21d206.achu.chat.controller.response;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;

/**
 * 에러 메시지 응답 DTO
 */
public class ChatErrorMessage {
  private final String code;
  private final String message;

  public ChatErrorMessage(ChatErrorType errorType) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
  }

  public ChatErrorMessage(ChatErrorType errorType, String message) {
    this.code = errorType.getCode().name();
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
