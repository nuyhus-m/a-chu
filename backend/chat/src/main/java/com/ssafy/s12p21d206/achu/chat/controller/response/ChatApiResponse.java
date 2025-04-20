package com.ssafy.s12p21d206.achu.chat.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;

public class ChatApiResponse<S> {

  private final ChatResultType result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final S data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final ChatErrorMessage error;

  private ChatApiResponse(ChatResultType result, S data, ChatErrorMessage error) {
    this.result = result;
    this.data = data;
    this.error = error;
  }

  public static ChatApiResponse<Void> success() {
    return new ChatApiResponse<>(ChatResultType.SUCCESS, null, null);
  }

  public static <S> ChatApiResponse<S> success(S data) {
    return new ChatApiResponse<>(ChatResultType.SUCCESS, data, null);
  }

  public static ChatApiResponse<Void> error(ChatErrorType error) {
    return new ChatApiResponse<>(ChatResultType.ERROR, null, new ChatErrorMessage(error));
  }

  public static ChatApiResponse<Void> error(ChatErrorType error, String message) {
    return new ChatApiResponse<>(ChatResultType.ERROR, null, new ChatErrorMessage(error, message));
  }

  public ChatResultType getResult() {
    return result;
  }

  public S getData() {
    return data;
  }

  public ChatErrorMessage getError() {
    return error;
  }

  public static class ChatErrorMessage {
    private final String code;
    private final String message;

    public ChatErrorMessage(ChatErrorType errorType) {
      this.code = errorType.getCode().name();
      this.message = errorType.getMessage();
    }

    public ChatErrorMessage(ChatErrorType errorType, String customMessage) {
      this.code = errorType.getCode().name();
      this.message = customMessage;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }
  }
}
