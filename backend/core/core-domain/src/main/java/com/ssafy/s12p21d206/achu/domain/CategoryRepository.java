package com.ssafy.s12p21d206.achu.domain;

import java.util.List;

public interface CategoryRepository {

  List<Category> findCategories();

  boolean existsById(Long categoryId);
}
