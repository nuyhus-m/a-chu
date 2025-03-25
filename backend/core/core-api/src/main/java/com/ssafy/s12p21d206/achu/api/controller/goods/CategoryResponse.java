package com.ssafy.s12p21d206.achu.api.controller.goods;

import com.ssafy.s12p21d206.achu.domain.Category;
import java.util.List;

public record CategoryResponse(Long id, String name) {

  public static List<CategoryResponse> of(List<Category> categories) {
    return categories.stream()
        .map(category -> new CategoryResponse(category.getId(), category.getName()))
        .toList();
  }

  public static CategoryResponse from(Category category) {
    return new CategoryResponse(category.getId(), category.getName());
  }
}
