package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.auth.domain.support.AuthDefaultDateTime;
import com.ssafy.s12p21d206.achu.auth.domain.verification.NewVerificationCode;
import com.ssafy.s12p21d206.achu.auth.domain.verification.Phone;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationCode;
import com.ssafy.s12p21d206.achu.auth.domain.verification.VerificationPurpose;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.util.UUID;

@Table(name = "verification_code")
@Entity
public class OneTimeCodeEntity extends AuthMetaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id; // java.util.uuid

  private String phoneNumber;
  private String code;

  @Enumerated(EnumType.STRING)
  private VerificationPurpose purpose;

  private boolean isVerified;

  private Duration expiresIn;

  protected OneTimeCodeEntity() {}

  public OneTimeCodeEntity(
      UUID id,
      String phoneNumber,
      String code,
      VerificationPurpose purpose,
      boolean isVerified,
      Duration expiresIn) {
    this.id = id;
    this.phoneNumber = phoneNumber;
    this.code = code;
    this.purpose = purpose;
    this.isVerified = isVerified;
    this.expiresIn = expiresIn;
  }

  public static OneTimeCodeEntity from(
      NewVerificationCode verificationCode, Phone phone, VerificationPurpose purpose) {
    return new OneTimeCodeEntity(
        null,
        phone.number(),
        verificationCode.code(),
        purpose,
        false,
        verificationCode.expiresIn());
  }

  public static OneTimeCodeEntity from(VerificationCode verificationCode) {
    return new OneTimeCodeEntity(
        verificationCode.getId(),
        verificationCode.getPhone().number(),
        verificationCode.getCode(),
        verificationCode.getPurpose(),
        verificationCode.isVerified(),
        verificationCode.getExpiresIn());
  }

  public VerificationCode toVerificationCode() {
    return new VerificationCode(
        this.id,
        new Phone(phoneNumber),
        code,
        purpose,
        expiresIn,
        new AuthDefaultDateTime(getCreatedAt(), getUpdatedAt()),
        isVerified);
  }
}
