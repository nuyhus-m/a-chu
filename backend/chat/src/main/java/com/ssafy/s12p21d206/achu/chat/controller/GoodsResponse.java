package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.goods.Goods;

public record GoodsResponse(Long id, String title, String thumbnailImageUrl) {

  public static GoodsResponse from(Goods goods) {
    return new GoodsResponse(goods.id(), goods.title(), goods.thumbnailImageUrl());
  }
}
