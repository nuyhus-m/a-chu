package com.ssafy.s12p21d206.achu.fcm.controller.support;

import com.ssafy.s12p21d206.achu.fcm.controller.FcmApiUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class FcmApiUserArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(FcmApiUser.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    Object userIdObj = request.getAttribute("userId");

    if (userIdObj == null) {
      throw new IllegalArgumentException("User ID not found in request attributes");
    }

    Long userId;
    try {
      userId = (Long) userIdObj;
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Invalid userId type in request attributes", e);
    }

    return new FcmApiUser(userId);
  }
}
