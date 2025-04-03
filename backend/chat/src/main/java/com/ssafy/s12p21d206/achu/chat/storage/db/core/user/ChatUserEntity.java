package com.ssafy.s12p21d206.achu.chat.storage.db.core.user;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import com.ssafy.s12p21d206.achu.chat.storage.db.core.support.ChatEntityStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

/**
 * 채팅 사용자 정보를 담는 불변 엔티티
 * 사용자 서비스에서 관리되는 데이터를 읽기 전용으로 미러링
 */
@Entity
@Immutable
@Table(name = "`user`")
public class ChatUserEntity {

  @Id
  private Long id;

  private String nickname;

  private String profileImageUrl;

  private ChatEntityStatus status;

  protected ChatUserEntity() {}

  public ChatUserProfile toProfile() {
    return ChatUserProfile.of(id, nickname, profileImageUrl);
  }
}
