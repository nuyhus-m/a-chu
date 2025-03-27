package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class PageValidator {
  public void validatePageParams(Long offset, Long limit) {
    if (offset == null || offset < 0) {
      throw new CoreException(CoreErrorType.INVALID_PAGING_OFFSET);
    }
    if (limit == null || limit <= 0) {
      throw new CoreException(CoreErrorType.INVALID_PAGING_LIMIT);
    }
  }
}
