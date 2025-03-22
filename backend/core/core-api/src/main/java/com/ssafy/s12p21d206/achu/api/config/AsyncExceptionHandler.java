package com.ssafy.s12p21d206.achu.api.config;

import java.lang.reflect.Method;

public interface AsyncExceptionHandler {
  boolean supports(Throwable exception);

  void handle(Throwable exception, Method method, Object... params);
}
