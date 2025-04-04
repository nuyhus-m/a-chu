package com.ssafy.s12p21d206.achu.chat.controller.support;

import com.ssafy.s12p21d206.achu.chat.controller.ChatApiUser;
import com.ssafy.s12p21d206.achu.chat.controller.response.ChatApiResponse;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebSocketExceptionHandlerAspect {

  // WebSocket destination 상수
  private static final String DESTINATION_USER_ERRORS = "/users/%d/errors";

  private final SimpMessagingTemplate messagingTemplate;

  public WebSocketExceptionHandlerAspect(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  /**
   * @WebSocketMessageHandler 애노테이션이 적용된 메서드를 대상으로 AOP 적용
   *                          예외 발생 시 적절한 오류 메시지를 클라이언트에게 전송
   */
  @Around("@annotation(com.ssafy.s12p21d206.achu.chat.controller.support.WebSocketMessageHandler)")
  public Object handleWebSocketException(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      // 원래 메서드 실행
      return joinPoint.proceed();
    } catch (ChatException e) {
      // 채팅 도메인 예외 처리
      sendErrorMessage(getUserId(joinPoint), e.getErrorType());
      return null;
    } catch (Exception e) {
      // 기타 예외 처리
      sendErrorMessage(getUserId(joinPoint), e);
      return null;
    }
  }

  /**
   * JoinPoint의 인자 중에서 ChatApiUser를 찾아 사용자 ID 반환
   */
  private Long getUserId(ProceedingJoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      if (arg instanceof ChatApiUser chatApiUser) {
        return chatApiUser.id();
      }
    }
    // 사용자 ID를 찾을 수 없는 경우 기본값 반환
    return -1L;
  }

  /**
   * 사용자에게 ChatException 에러 메시지 전송
   */
  private void sendErrorMessage(Long userId, ChatErrorType errorType) {
    String destination = String.format(DESTINATION_USER_ERRORS, userId);
    messagingTemplate.convertAndSend(destination, ChatApiResponse.error(errorType));
  }

  /**
   * 사용자에게 일반 Exception 에러 메시지 전송
   */
  private void sendErrorMessage(Long userId, Exception e) {
    String destination = String.format(DESTINATION_USER_ERRORS, userId);
    messagingTemplate.convertAndSend(
        destination, ChatApiResponse.error(ChatErrorType.INTERNAL_SERVER_ERROR, e.getMessage()));
  }
}
