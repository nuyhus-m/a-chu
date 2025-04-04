package com.ssafy.s12p21d206.achu.storage.db.core.goods;

import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "goods")
@Immutable
public class ChatGoodsEntity extends ChatBaseEntity {

  private String title;

  private String thumbnailImageUrl;

  protected ChatGoodsEntity() {}
}
