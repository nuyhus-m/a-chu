package com.ssafy.s12p21d206.achu.storage.db.core.goods;

import com.ssafy.s12p21d206.achu.chat.domain.goods.ChatGoodsRepository;
import com.ssafy.s12p21d206.achu.chat.domain.goods.Goods;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class ChatGoodsCoreRepository implements ChatGoodsRepository {

  private final ChatGoodsJpaRepository chatGoodsJpaRepository;

  public ChatGoodsCoreRepository(ChatGoodsJpaRepository chatGoodsJpaRepository) {
    this.chatGoodsJpaRepository = chatGoodsJpaRepository;
  }

  @Override
  public Optional<Goods> findById(Long id) {
    return chatGoodsJpaRepository.findById(id).map(ChatGoodsEntity::toGoods);
  }

  @Override
  public boolean existsById(Long goodsId) {
    return chatGoodsJpaRepository.existsById(goodsId);
  }

  @Override
  public List<Goods> findByIdIn(Set<Long> goodIds) {
    return chatGoodsJpaRepository.findByIdIn(goodIds).stream()
        .map(ChatGoodsEntity::toGoods)
        .toList();
  }
}
