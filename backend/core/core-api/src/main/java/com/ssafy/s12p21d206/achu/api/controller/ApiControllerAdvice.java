package com.ssafy.s12p21d206.achu.api.controller;

import com.ssafy.s12p21d206.achu.api.error.CoreApiErrorType;
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

  @ExceptionHandler(CoreApiException.class)
  public ResponseEntity<ApiResponse<?>> handleCoreApiException(CoreApiException e) {
    switch (e.getErrorType().getLevel()) {
      case ERROR:
        log.error("CoreApiException : {}", e.getMessage(), e);
      case WARN:
        log.warn("CoreApiException : {}", e.getMessage(), e);
      default:
        log.info("CoreApiException : {}", e.getMessage(), e);
    }

    return new ResponseEntity<>(
        ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
  }

  @ExceptionHandler(CoreException.class)
  public ResponseEntity<ApiResponse<?>> handleCoreException(CoreException e) {
    switch (e.getErrorType().getLevel()) {
      case ERROR:
        log.error("CoreException : {}", e.getMessage(), e);
      case WARN:
        log.warn("CoreException : {}", e.getMessage(), e);
      default:
        log.info("CoreException : {}", e.getMessage(), e);
    }

    HttpStatus status = e.getErrorType().getKind() == CoreErrorKind.CLIENT_ERROR
        ? HttpStatus.BAD_REQUEST
        : HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), status);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception e) {
    log.error("Exception : {}", e.getMessage(), e);
    return new ResponseEntity<>(
        ApiResponse.error(CoreApiErrorType.INTERNAL_SERVER_ERROR),
        CoreApiErrorType.INTERNAL_SERVER_ERROR.getStatus());
  }
}
