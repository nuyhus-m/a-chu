package com.ssafy.s12p21d206.achu.chat.controller;

public record DefaultIdResponse(Long id) {
  public static DefaultIdResponse of(Long id) {
    return new DefaultIdResponse(id);
  }
}
