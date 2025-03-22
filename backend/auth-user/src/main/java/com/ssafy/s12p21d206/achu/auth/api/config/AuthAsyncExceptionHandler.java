package com.ssafy.s12p21d206.achu.auth.api.config;

import com.ssafy.s12p21d206.achu.api.config.AsyncExceptionHandler;
import com.ssafy.s12p21d206.achu.auth.domain.error.AuthCoreException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthAsyncExceptionHandler implements AsyncExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public boolean supports(Throwable exception) {
    return exception instanceof AuthCoreException;
  }

  @Override
  public void handle(Throwable e, Method method, Object... params) {
    AuthCoreException authCoreException = (AuthCoreException) e;
    switch (authCoreException.getErrorType().getLevel()) {
      case ERROR -> log.error("CoreException : {}", e.getMessage(), e);
      case WARN -> log.warn("CoreException : {}", e.getMessage(), e);
      default -> log.info("CoreException : {}", e.getMessage(), e);
    }
  }
}
