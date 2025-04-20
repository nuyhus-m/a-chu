package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserFinder userFinder;

  public UserService(UserFinder userFinder) {
    this.userFinder = userFinder;
  }

  public UserDetail findSellerInfo(User user) {
    return userFinder.findUserDetail(user);
  }
}
