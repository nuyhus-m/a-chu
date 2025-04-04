package com.ssafy.s12p21d206.achu.storage.db.core.goods;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatGoodsJpaRepository extends JpaRepository<ChatGoodsEntity, Long> {

  @Query("SELECT g FROM ChatGoodsEntity g WHERE g.id IN :ids")
  List<ChatGoodsEntity> findByIdIn(Set<Long> ids);
}
