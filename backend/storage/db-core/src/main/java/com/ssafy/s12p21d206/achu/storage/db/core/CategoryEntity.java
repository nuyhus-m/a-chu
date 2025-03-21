package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "category")
@Entity
public class CategoryEntity extends BaseEntity {

  private final String name;

  protected CategoryEntity(String name) {
    this.name = name;
  }

  public Category toCategory() {
    return new Category(getId(), name);
  }
}
