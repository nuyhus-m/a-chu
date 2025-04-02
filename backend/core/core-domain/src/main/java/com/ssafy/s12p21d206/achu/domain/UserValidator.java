package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.error.CoreErrorType;
import com.ssafy.s12p21d206.achu.domain.error.CoreException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
  private final UserRepository userRepository;

  public UserValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void validateExists(Long id) {
    if (!userRepository.existsById(id)) {
      throw new CoreException(CoreErrorType.DATA_NOT_FOUND);
    }
  }
}
