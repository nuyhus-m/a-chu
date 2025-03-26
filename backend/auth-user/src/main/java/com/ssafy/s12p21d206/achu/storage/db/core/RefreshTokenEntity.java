package com.ssafy.s12p21d206.achu.storage.db.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class RefreshTokenEntity {

  @Id
  private String username;

  private String refreshToken;

  private LocalDateTime expirationTime;

  protected RefreshTokenEntity() {}

  public RefreshTokenEntity(String username, String refreshToken, LocalDateTime expirationTime) {
    this.username = username;
    this.refreshToken = refreshToken;
    this.expirationTime = expirationTime;
  }

  public String getUsername() {
    return username;
  }

  public LocalDateTime getExpirationTime() {
    return expirationTime;
  }

  public String getRefreshToken() {
    return refreshToken;
  }
}
