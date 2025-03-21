package com.ssafy.s12p21d206.achu.auth.api.controller;

import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiErrorType;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthCoreApiException;
import com.ssafy.s12p21d206.achu.auth.api.error.AuthErrorMessage;
import com.ssafy.s12p21d206.achu.auth.api.response.AuthApiResponse;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreErrorKind;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = AuthApiControllerAdvice.class)
public class AuthApiControllerAdvice {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String CORE_API_EXCEPTION_LOG_FORMAT = "CoreApiException : {}";
  private static final String CORE_EXCEPTION_LOG_FORMAT = "CoreException : {}";
  private static final String EXCEPTION_LOG_FORMAT = "Exception : {}";

  @ExceptionHandler(AuthCoreApiException.class)
  public ResponseEntity<AuthApiResponse<Void>> handleCoreApiException(AuthCoreApiException e) {
    switch (e.getErrorType().getLevel()) {
      case ERROR:
        log.error(CORE_API_EXCEPTION_LOG_FORMAT, e.getMessage(), e);
        break;
      case WARN:
        log.warn(CORE_API_EXCEPTION_LOG_FORMAT, e.getMessage(), e);
        break;
      default:
        log.info(CORE_API_EXCEPTION_LOG_FORMAT, e.getMessage(), e);
        break;
    }

    AuthErrorMessage errorMessage = new AuthErrorMessage(e.getErrorType(), e.getMessage());
    return new ResponseEntity<>(
        AuthApiResponse.error(errorMessage), e.getErrorType().getStatus());
  }

  @ExceptionHandler(AuthCoreException.class)
  public ResponseEntity<AuthApiResponse<Void>> handleCoreException(AuthCoreException e) {
    switch (e.getErrorType().getLevel()) {
      case ERROR:
        log.error(CORE_EXCEPTION_LOG_FORMAT, e.getMessage(), e);
        break;
      case WARN:
        log.warn(CORE_EXCEPTION_LOG_FORMAT, e.getMessage(), e);
        break;
      default:
        log.info(CORE_EXCEPTION_LOG_FORMAT, e.getMessage(), e);
        break;
    }

    HttpStatus status = e.getErrorType().getKind() == AuthCoreErrorKind.CLIENT_ERROR
        ? HttpStatus.BAD_REQUEST
        : HttpStatus.INTERNAL_SERVER_ERROR;

    AuthErrorMessage errorMessage = new AuthErrorMessage(e.getErrorType(), e.getMessage());
    return new ResponseEntity<>(AuthApiResponse.error(errorMessage), status);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<AuthApiResponse<Void>> handleException(Exception e) {
    log.error(EXCEPTION_LOG_FORMAT, e.getMessage(), e);
    AuthCoreApiErrorType errorType = AuthCoreApiErrorType.INTERNAL_SERVER_ERROR;
    AuthErrorMessage errorMessage = new AuthErrorMessage(errorType, e.getMessage());
    return new ResponseEntity<>(
        AuthApiResponse.error(errorMessage),
        AuthCoreApiErrorType.INTERNAL_SERVER_ERROR.getStatus());
  }
}
