package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.fcm.domain.FcmToken;
import com.ssafy.s12p21d206.achu.fcm.domain.NewFcmToken;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "fcm_token")
@Entity
public class FcmTokenEntity extends FcmBaseEntity {
  private Long userId;
  private String fcmToken;

  protected FcmTokenEntity() {}

  public FcmTokenEntity(Long userId, String fcmToken) {
    this.userId = userId;
    this.fcmToken = fcmToken;
  }

  public FcmToken toFcmToken() {
    return new FcmToken(getId(), this.userId, this.fcmToken);
  }

  public void update(NewFcmToken newFcmToken) {
    this.fcmToken = newFcmToken.fcmToken();
  }
}
