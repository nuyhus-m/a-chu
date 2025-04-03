package com.ssafy.s12p21d206.achu.chat.storage.db.core.user;

import com.ssafy.s12p21d206.achu.chat.storage.db.core.support.ChatEntityStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 채팅 사용자 정보에 접근하는 JPA Repository
 */
public interface ChatUserJpaRepository extends JpaRepository<ChatUserEntity, Long> {

  public Optional<ChatUserEntity> findByIdAndStatus(Long id, ChatEntityStatus status);
}
