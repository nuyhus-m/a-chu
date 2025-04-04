package com.ssafy.s12p21d206.achu.storage.db.core.user;

import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatUserJpaRepository extends JpaRepository<ChatUserEntity, Long> {

  Optional<ChatUserEntity> findByIdAndEntityStatus(Long id, ChatEntityStatus entityStatus);

  @Query("SELECT u FROM ChatUserEntity u WHERE u.id IN :userIds")
  List<ChatUserEntity> findByUserIdsIn(List<Long> userIds);
}
