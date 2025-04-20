package com.ssafy.s12p21d206.achu.domain;

import java.util.Optional;

public interface UserRepository {

  Optional<UserDetail> findUserDetail(User user);

  boolean existsById(Long id);
}
