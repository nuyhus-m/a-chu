package com.ssafy.s12p21d206.achu.chat.controller.support;

import com.ssafy.s12p21d206.achu.chat.controller.ChatApiUser;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public class ChatApiUserStompArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(ChatApiUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, Message<?> message) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    Object userIdObj = accessor.getHeader("X-UserId");

    if (userIdObj == null) {
      throw new IllegalArgumentException("User ID not found in request attributes");
    }

    Long userId;
    try {
      userId = (Long) userIdObj;
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Invalid userId type in request attributes", e);
    }

    return new ChatApiUser(userId);
  }
}
