package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Category;
import com.ssafy.s12p21d206.achu.domain.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryCoreRepository implements CategoryRepository {

  private final CategoryJpaRepository categoryJpaRepository;

  public CategoryCoreRepository(CategoryJpaRepository categoryJpaRepository) {
    this.categoryJpaRepository = categoryJpaRepository;
  }

  @Override
  public List<Category> findCategories() {
    List<CategoryEntity> categoryEntities = categoryJpaRepository.findAll();
    return categoryEntities.stream().map(CategoryEntity::toCategory).toList();
  }

  @Override
  public Category findCategoryInfo(Long id) {
    CategoryEntity categoryEntity = categoryJpaRepository
        .findByIdAndEntityStatus(id, EntityStatus.ACTIVE)
        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리 입니다."));
    return categoryEntity.toCategory();
  }
}
