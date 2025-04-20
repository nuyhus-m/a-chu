package com.ssafy.s12p21d206.achu.chat.domain.goods;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import org.springframework.stereotype.Component;

@Component
public class ChatGoodsValidator {
  private final ChatGoodsRepository chatGoodsRepository;

  public ChatGoodsValidator(ChatGoodsRepository chatGoodsRepository) {
    this.chatGoodsRepository = chatGoodsRepository;
  }

  public void validateExists(Long goodsId) {
    if (!chatGoodsRepository.existsById(goodsId)) {
      throw new ChatException(ChatErrorType.GOODS_NOT_FOUND);
    }
  }
}
