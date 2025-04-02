package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class UserFinder {

  private final UserRepository userRepository;

  public UserFinder(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetail findUserDetail(User user) {
    return userRepository
        .findUserDetail(user)
        .orElseThrow(() -> new CoreException(CoreErrorType.DATA_NOT_FOUND));
  }
}
