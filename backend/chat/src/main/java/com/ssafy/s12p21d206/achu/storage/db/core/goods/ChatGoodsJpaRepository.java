package com.ssafy.s12p21d206.achu.storage.db.core.goods;

import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGoodsJpaRepository extends JpaRepository<ChatGoodsEntity, Long> {

  boolean existsByIdAndEntityStatus(Long id, ChatEntityStatus entityStatus);
}
