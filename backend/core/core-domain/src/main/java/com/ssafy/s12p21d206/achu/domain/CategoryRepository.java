package com.ssafy.s12p21d206.achu.domain;

import java.util.List;

public interface CategoryRepository {

  List<Category> findCategories();

  Category findCategoryInfo(Long categoryId);

  boolean existsById(Long categoryId);
}
