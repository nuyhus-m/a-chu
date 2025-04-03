package com.ssafy.s12p21d206.achu.chat.controller.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WebSocket 메시지 핸들러를 위한 애노테이션.
 * 이 애노테이션이 적용된 메서드는 AOP를 통해 예외 처리와 응답 처리가 자동으로 수행됩니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSocketMessageHandler {}
