package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.TradeHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeHistoryJpaRepository extends JpaRepository<TradeHistoryEntity, Long> {
  List<TradeHistory> findBySellerIdAndEntityStatus(Long sellerId, EntityStatus entityStatus);

  List<TradeHistory> findByBuyerIdAndEntityStatus(Long buyerId, EntityStatus entityStatus);
}
