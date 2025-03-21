package com.ssafy.s12p21d206.achu.domain;

public record Category(Long id, String name) {

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
