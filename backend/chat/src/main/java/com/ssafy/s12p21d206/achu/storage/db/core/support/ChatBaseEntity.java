package com.ssafy.s12p21d206.achu.storage.db.core.support;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class ChatBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "entityStatus", columnDefinition = "VARCHAR")
  private ChatEntityStatus entityStatus = ChatEntityStatus.ACTIVE;

  @CreationTimestamp
  private LocalDateTime createdAt;

  protected ChatBaseEntity() {}

  protected ChatBaseEntity(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void active() {
    this.entityStatus = ChatEntityStatus.ACTIVE;
  }

  public void delete() {
    this.entityStatus = ChatEntityStatus.DELETED;
  }

  public boolean isActive() {
    return this.entityStatus == ChatEntityStatus.ACTIVE;
  }

  public boolean isDeleted() {
    return this.entityStatus == ChatEntityStatus.DELETED;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
