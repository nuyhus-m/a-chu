package com.ssafy.s12p21d206.achu.storage.db.core;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
  Optional<CategoryEntity> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);

  boolean existsByIdAndEntityStatus(Long id, EntityStatus entityStatus);
}
