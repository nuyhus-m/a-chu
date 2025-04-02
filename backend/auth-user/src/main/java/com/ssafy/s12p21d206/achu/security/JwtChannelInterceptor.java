package com.ssafy.s12p21d206.achu.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

  private final JwtService jwtService;

  public JwtChannelInterceptor(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Authorization 헤더가 필요합니다.");
    }

    String token = authorizationHeader.substring(7);
    AuthUserDetails authUserDetails = jwtService.resolveAccessToken(token);
    Authentication authentication = getAuthenticated(authUserDetails);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    accessor.setHeader("X-UserId", authUserDetails.getId());
    return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
  }

  private static UsernamePasswordAuthenticationToken getAuthenticated(
      AuthUserDetails authUserDetails) {
    return UsernamePasswordAuthenticationToken.authenticated(
        authUserDetails.getId(),
        null,
        authUserDetails.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
  }
}
