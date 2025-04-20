package com.ssafy.s12p21d206.achu.api.controller;

import com.ssafy.s12p21d206.achu.domain.User;

public record ApiUser(Long id) {

  public User toUser() {
    return new User(id);
  }

  public User fromUser(Long id) {
    return new User(id);
  }
}
