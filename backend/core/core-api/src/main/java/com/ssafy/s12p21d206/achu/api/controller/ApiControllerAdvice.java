package com.ssafy.s12p21d206.achu.api.controller;

import com.ssafy.s12p21d206.achu.api.error.CoreApiException;
import com.ssafy.s12p21d206.achu.api.response.ApiResponse;
import com.ssafy.s12p21d206.achu.domain.error.CoreErrorKind;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ApiControllerAdvice.class)
public class ApiControllerAdvice {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String CORE_API_EXCEPTION_LOG_FORMAT = "CoreApiException : {}";
  private static final String CORE_EXCEPTION_LOG_FORMAT = "CoreException : {}";

  @ExceptionHandler(CoreApiException.class)
  public ResponseEntity<ApiResponse<Void>> handleCoreApiException(CoreApiException e) {
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

    return new ResponseEntity<>(
        ApiResponse.error(e.getErrorType()), e.getErrorType().getStatus());
  }

  @ExceptionHandler(CoreException.class)
  public ResponseEntity<ApiResponse<Void>> handleCoreException(CoreException e) {
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

    HttpStatus status = e.getErrorType().getKind() == CoreErrorKind.CLIENT_ERROR
        ? HttpStatus.BAD_REQUEST
        : HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(ApiResponse.error(e.getErrorType()), status);
  }
}
