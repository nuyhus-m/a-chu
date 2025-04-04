package com.ssafy.s12p21d206.achu.storage.db.core.goods;

import com.ssafy.s12p21d206.achu.chat.domain.goods.ChatGoodsRepository;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatEntityStatus;
import org.springframework.stereotype.Repository;

@Repository
public class ChatGoodsCoreRepository implements ChatGoodsRepository {

  private final ChatGoodsJpaRepository chatGoodsJpaRepository;

  public ChatGoodsCoreRepository(ChatGoodsJpaRepository chatGoodsJpaRepository) {
    this.chatGoodsJpaRepository = chatGoodsJpaRepository;
  }

  @Override
  public boolean existsById(Long goodsId) {
    return chatGoodsJpaRepository.existsByIdAndEntityStatus(goodsId, ChatEntityStatus.ACTIVE);
  }
}
