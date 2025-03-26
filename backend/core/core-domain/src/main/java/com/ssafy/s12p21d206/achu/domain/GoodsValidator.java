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

  public void validateExists(Long id) {
    if (!goodsRepository.existsByIdAndEntityStatus(id)) {
      throw new CoreException(CoreErrorType.DATA_NOT_FOUND);
    }
  }

  public void validateOwner(Long id, Long userId) {
    if (!goodsRepository.existsByIdAndUserIdAndEntityStatus(id, userId)) {
      throw new CoreException(CoreErrorType.CANNOT_ACCESS_GOODS);
    }
  }

  public void validateSelling(Long id) {
    if (!goodsRepository.existByIdAndTradeStatus(id)) {
      throw new CoreException(CoreErrorType.GOODS_ALREADY_SOLD);
    }
  }

}
