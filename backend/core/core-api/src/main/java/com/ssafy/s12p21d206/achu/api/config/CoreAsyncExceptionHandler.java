package com.ssafy.s12p21d206.achu.api.config;

import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CoreAsyncExceptionHandler implements AsyncExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public boolean supports(Throwable exception) {
    return exception instanceof CoreException;
  }

  @Override
  public void handle(Throwable e, Method method, Object... params) {
    CoreException coreException = (CoreException) e;
    switch (coreException.getErrorType().getLevel()) {
      case ERROR -> log.error("CoreException : {}", e.getMessage(), e);
      case WARN -> log.warn("CoreException : {}", e.getMessage(), e);
      default -> log.info("CoreException : {}", e.getMessage(), e);
    }
  }
}
