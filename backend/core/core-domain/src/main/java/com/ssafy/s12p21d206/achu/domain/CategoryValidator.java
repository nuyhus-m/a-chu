package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

  private final CategoryRepository categoryRepository;

  public CategoryValidator(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void validateExists(Long categoryId) {
    if (!categoryRepository.existsById(categoryId)) {
      throw new CoreException(CoreErrorType.DATA_NOT_FOUND);
    }
  }
}
