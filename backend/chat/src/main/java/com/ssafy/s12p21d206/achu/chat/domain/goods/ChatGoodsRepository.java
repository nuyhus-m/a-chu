package com.ssafy.s12p21d206.achu.chat.domain.goods;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatGoodsRepository {
  Optional<Goods> findById(Long id);

  boolean existsById(Long goodsId);

  List<Goods> findByIdIn(Set<Long> goodIds);
}
