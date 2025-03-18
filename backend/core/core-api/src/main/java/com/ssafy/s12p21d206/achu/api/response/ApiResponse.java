package com.ssafy.s12p21d206.achu.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.s12p21d206.achu.api.error.CoreApiErrorType;
import com.ssafy.s12p21d206.achu.api.error.ErrorMessage;
import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;

public class ApiResponse<S> {

  private final ResultType result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final S data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final ErrorMessage error;

  private ApiResponse(ResultType result, S data, ErrorMessage error) {
    this.result = result;
    this.data = data;
    this.error = error;
  }

  public static ApiResponse<Void> success() {
    return new ApiResponse<>(ResultType.SUCCESS, null, null);
  }

  public static <S> ApiResponse<S> success(S data) {
    return new ApiResponse<>(ResultType.SUCCESS, data, null);
  }

  public static ApiResponse<Void> error(CoreApiErrorType error) {
    return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error));
  }

  public static ApiResponse<Void> error(CoreErrorType error) {
    return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error));
  }

  public ResultType getResult() {
    return result;
  }

  public Object getData() {
    return data;
  }

  public ErrorMessage getError() {
    return error;
  }
}
