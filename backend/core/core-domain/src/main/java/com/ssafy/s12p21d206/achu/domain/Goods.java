package com.ssafy.s12p21d206.achu.domain;

import java.time.LocalDateTime;

public record Goods(Long id, String title, String imgUrl, Long price, LocalDateTime createdAt) {

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public Long getPrice() {
    return price;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
