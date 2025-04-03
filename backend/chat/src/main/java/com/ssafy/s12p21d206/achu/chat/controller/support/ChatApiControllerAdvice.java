package com.ssafy.s12p21d206.achu.chat.controller.support;

import com.ssafy.s12p21d206.achu.chat.controller.response.ChatApiResponse;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorKind;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackageClasses = ChatApiControllerAdvice.class)
public class ChatApiControllerAdvice {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String CORE_EXCEPTION_LOG_FORMAT = "CoreException : {}";

  @ExceptionHandler(ChatException.class)
  public ResponseEntity<ChatApiResponse<Void>> handleCoreException(ChatException e) {
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

    HttpStatus status = e.getErrorType().getKind() == ChatErrorKind.CLIENT_ERROR
        ? HttpStatus.BAD_REQUEST
        : HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(ChatApiResponse.error(e.getErrorType()), status);
  }
}
