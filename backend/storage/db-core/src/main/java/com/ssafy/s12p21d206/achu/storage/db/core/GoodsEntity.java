package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.Goods;
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

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getImgUrls() {
    return imgUrls;
  }

  public TradeStatus getTradeStatus() {
    return tradeStatus;
  }

  public Long getPrice() {
    return price;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getBabyId() {
    return babyId;
  }

  public Goods toGoods() {
    return new Goods(getId(), title, imgUrls.get(0), price, getCreatedAt());
  }
}
