package com.ssafy.s12p21d206.achu.storage.db.core;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyJpaRepository extends JpaRepository<BabyEntity, Long> {
  List<BabyEntity> findByUserId(Long userId);

  Optional<BabyEntity> findByIdAndEntityStatus(Long id, EntityStatus status);
}
