package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.support.TradeStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsJpaRepository extends JpaRepository<GoodsEntity, Long> {

  // 조회할때 판매중인것들만 가져오게 수정
  List<GoodsEntity> findAllByEntityStatus(Pageable pageable, EntityStatus entityStatus);

  List<GoodsEntity> findAllByCategoryIdAndEntityStatus(
      Long categoryId, Pageable pageable, EntityStatus entityStatus);


  boolean existsByIdAndEntityStatus(Long goodsId, EntityStatus entityStatus);

  Optional<GoodsEntity> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);

  Boolean existsByIdAndEntityStatus(Long id, EntityStatus entityStatus);

  Boolean existsByIdAndUserIdAndEntityStatus(Long id, Long userId, EntityStatus entityStatus);

  Boolean existsByIdAndTradeStatus(Long id, TradeStatus tradeStatus);
}
