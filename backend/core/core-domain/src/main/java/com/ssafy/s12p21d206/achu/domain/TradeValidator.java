package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class TradeValidator {
  public void validateSellerIsNotBuyer(Long sellerId, Long buyerId) {
    if (sellerId.equals(buyerId)) {
      throw new CoreException(CoreErrorType.SELLER_CANNOT_BE_BUYER);
    }
  }
}
