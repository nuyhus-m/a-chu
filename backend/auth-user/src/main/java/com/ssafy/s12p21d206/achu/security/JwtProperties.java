package com.ssafy.s12p21d206.achu.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

  @Value("${achu.jwt.properties.secret}")
  private String secret;

  @Value("${achu.jwt.properties.access-token.expiration-seconds}")
  private Long accessTokenExpirationSeconds;

  @Value("${achu.jwt.properties.refresh-token.expiration-seconds}")
  private Long refreshTokenExpirationSeconds;

  @Value("${achu.jwt.properties.refresh-token.renew-available-seconds}")
  private Long refreshTokenRenewAvailableSeconds;

  public String getSecret() {
    return secret;
  }

  public Long getAccessTokenExpirationSeconds() {
    return accessTokenExpirationSeconds;
  }

  public Long getRefreshTokenExpirationSeconds() {
    return refreshTokenExpirationSeconds;
  }

  public Long getRefreshTokenRenewAvailableSeconds() {
    return refreshTokenRenewAvailableSeconds;
  }
}
