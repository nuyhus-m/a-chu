package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.domain.chat.ChatRoom;
import com.ssafy.s12p21d206.achu.storage.db.core.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "chat_room")
public class ChatRoomEntity extends BaseEntity {

  private Long goodsId;

  private Long sellerId;

  private Long buyerId;

  protected ChatRoomEntity() {}

  public ChatRoomEntity(Long goodsId, Long sellerId, Long buyerId) {
    this.goodsId = goodsId;
    this.sellerId = sellerId;
    this.buyerId = buyerId;
  }

  public ChatRoom toChatRoom() {
    return new ChatRoom(getId(), this.goodsId, this.sellerId, this.buyerId);
  }
}
