package com.ssafy.s12p21d206.achu.storage.db.core;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class AuthMetaEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "entityStatus", columnDefinition = "VARCHAR")
  private AuthEntityStatus entityStatus = AuthEntityStatus.ACTIVE;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public void active() {
    this.entityStatus = AuthEntityStatus.ACTIVE;
  }

  public void delete() {
    this.entityStatus = AuthEntityStatus.DELETED;
  }

  public boolean isActive() {
    return this.entityStatus == AuthEntityStatus.ACTIVE;
  }

  public boolean isDeleted() {
    return this.entityStatus == AuthEntityStatus.DELETED;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
