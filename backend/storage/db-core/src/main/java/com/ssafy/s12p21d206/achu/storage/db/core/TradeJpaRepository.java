package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.TradeType;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeJpaRepository extends JpaRepository<TradeEntity, Long> {

  @Query(
      "SELECT t FROM TradeEntity t where ((:tradeType = 'PURCHASE' AND t.buyerId = :userId) OR (:tradeType = 'SALE' AND t.sellerId = :userId)) AND t.entityStatus = :entityStatus")
  List<TradeEntity> findByUserIdAndEntityStatus(
      Long userId, TradeType tradeType, Pageable pageable, EntityStatus entityStatus);
}
