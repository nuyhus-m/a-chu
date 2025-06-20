package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class GoodsValidator {

  private final GoodsRepository goodsRepository;

  public GoodsValidator(GoodsRepository goodsRepository) {
    this.goodsRepository = goodsRepository;
  }

  public void validateExists(Long goodsId) {
    if (!goodsRepository.existsById(goodsId)) {
      throw new CoreException(CoreErrorType.DATA_NOT_FOUND);
    }
  }

  public void validateOwner(Long goodsId, Long userId) {
    if (!goodsRepository.existsByIdAndUserId(goodsId, userId)) {
      throw new CoreException(CoreErrorType.CANNOT_ACCESS_GOODS);
    }
  }

  public void validateIsSelling(Long goodsId) {
    if (!goodsRepository.isSelling(goodsId)) {
      throw new CoreException(CoreErrorType.GOODS_ALREADY_SOLD);
    }
  }
}
