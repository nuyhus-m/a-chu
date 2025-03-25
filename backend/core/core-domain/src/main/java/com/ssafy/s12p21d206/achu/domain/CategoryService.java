package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryReader categoryReader;

  public CategoryService(CategoryReader categoryReader) {
    this.categoryReader = categoryReader;
  }

  public List<Category> findCategories() {
    return categoryReader.readCategories();
  }

  public Category findCategoryInfo(Long id) {
    return categoryReader.readCategoryInfo(id);
  }
}
