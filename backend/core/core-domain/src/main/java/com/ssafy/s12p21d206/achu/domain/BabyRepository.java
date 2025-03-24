package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import java.util.Optional;

public interface BabyRepository {

  Baby save(User user, NewBaby newBaby);

  List<Baby> findByUser(User user);

  Optional<Baby> findById(Long id);

  Long delete(Long id);

  boolean existsById(Long id);
}
