package com.ssafy.s12p21d206.achu.chat.domain.goods;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ChatGoodsReader {
  private final ChatGoodsRepository goodsRepository;

  public ChatGoodsReader(ChatGoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public Goods readById(Long goodsId) {
    return goodsRepository
        .findById(goodsId)
        .orElseThrow(() -> new ChatException(ChatErrorType.GOODS_NOT_FOUND));
  }

  public List<Goods> readInGoodsIds(Set<Long> goodIds) {
    return goodsRepository.findByIdIn(goodIds);
  }
}
