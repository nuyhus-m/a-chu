package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CategoryReader {

  private final CategoryRepository categoryRepository;

  public CategoryReader(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> readCategories() {
    return categoryRepository.findCategories();
  }
}
