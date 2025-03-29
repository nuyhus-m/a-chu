package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Goods;
import com.ssafy.s12p21d206.achu.domain.GoodsDetail;
import com.ssafy.s12p21d206.achu.domain.TradeStatus;
import com.ssafy.s12p21d206.achu.domain.User;
import com.ssafy.s12p21d206.achu.storage.db.core.converter.ImgUrlListJsonConverter;
import jakarta.persistence.*;
import java.util.List;

@Table(name = "goods")
@Entity
public class GoodsEntity extends BaseEntity {

  private String title;

  private String description;

  @Convert(converter = ImgUrlListJsonConverter.class)
  @Column(name = "imgUrls", columnDefinition = "json")
  private List<String> imgUrls;

  @Enumerated(EnumType.STRING)
  @Column(name = "tradeStatus", columnDefinition = "VARCHAR")
  private TradeStatus tradeStatus = TradeStatus.SELLING;

  private Long price;

  private Long categoryId;

  private Long userId;

  private Long babyId;

  protected GoodsEntity() {}

  public GoodsEntity(
      String title,
      String description,
      List<String> imgUrls,
      TradeStatus tradeStatus,
      Long price,
      Long categoryId,
      Long userId,
      Long babyId) {
    this.title = title;
    this.description = description;
    this.imgUrls = imgUrls;
    this.tradeStatus = tradeStatus;
    this.price = price;
    this.categoryId = categoryId;
    this.userId = userId;
    this.babyId = babyId;
  }

  public Goods toGoods() {
    return new Goods(getId(), this.title, this.imgUrls.get(0), this.price, getCreatedAt());
  }

  public GoodsDetail toGoodsDetail() {
    return new GoodsDetail(
        getId(),
        this.title,
        this.description,
        this.imgUrls,
        this.tradeStatus,
        this.price,
        getCreatedAt(),
        this.categoryId,
        this.userId,
        this.babyId);
  }

  public User toUserId() {
    return new User(userId);
  }
}
