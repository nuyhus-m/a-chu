package com.ssafy.s12p21d206.achu.storage.db.core;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsJpaRepository extends JpaRepository<GoodsEntity, Long> {

  List<GoodsEntity> findAllByAndEntityStatus(Pageable pageable, EntityStatus entityStatus);

  List<GoodsEntity> findAllByCategoryIdAndEntityStatus(
      Long categoryId, Pageable pageable, EntityStatus entityStatus);
}
