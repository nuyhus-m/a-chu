package com.ssafy.s12p21d206.achu.storage.db.core.goods;

import com.ssafy.s12p21d206.achu.chat.domain.goods.ChatGoodsTradeStatus;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "goods")
@Immutable
public class ChatGoodsEntity extends ChatBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String thumbnailImageUrl;

  private Long price;

  @Enumerated(EnumType.STRING)
  @Column(name = "tradeStatus", columnDefinition = "VARCHAR")
  private ChatGoodsTradeStatus tradeStatus = ChatGoodsTradeStatus.SELLING;

  protected ChatGoodsEntity() {}
}
