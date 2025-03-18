package com.ssafy.s12p21d206.achu.auth.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiErrorType;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthErrorMessage;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorType;

public class AuthApiResponse<S> {

  private final AuthResultType result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final S data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final AuthErrorMessage error;

  private AuthApiResponse(AuthResultType result, S data, AuthErrorMessage error) {
    this.result = result;
    this.data = data;
    this.error = error;
  }

  public static AuthApiResponse<Void> success() {
    return new AuthApiResponse<>(AuthResultType.SUCCESS, null, null);
  }

  public static <S> AuthApiResponse<S> success(S data) {
    return new AuthApiResponse<>(AuthResultType.SUCCESS, data, null);
  }

  public static AuthApiResponse<Void> error(AuthCoreApiErrorType error) {
    return new AuthApiResponse<>(AuthResultType.ERROR, null, new AuthErrorMessage(error));
  }

  public static AuthApiResponse<Void> error(AuthCoreErrorType error) {
    return new AuthApiResponse<>(AuthResultType.ERROR, null, new AuthErrorMessage(error));
  }

  public AuthResultType getResult() {
    return result;
  }

  public Object getData() {
    return data;
  }

  public AuthErrorMessage getError() {
    return error;
  }
}
