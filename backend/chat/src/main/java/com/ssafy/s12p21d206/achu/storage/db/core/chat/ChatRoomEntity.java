package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.chat.domain.NewChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.storage.db.core.support.ChatBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "chat_room")
@Entity
public class ChatRoomEntity extends ChatBaseEntity {

  private Long goodsId;

  private Long sellerId;
  private Long buyerId;

  private Long sellerLastReadMessageId;
  private Long buyerLastReadMessageId;

  private boolean isSellerLeft;
  private boolean isBuyerLeft;

  protected ChatRoomEntity() {}

  public ChatRoomEntity(Long goodsId, Long sellerId, Long buyerId) {
    this.goodsId = goodsId;
    this.sellerId = sellerId;
    this.buyerId = buyerId;
  }

  public static ChatRoomEntity fromNewChatRoom(ChatUser buyer, NewChatRoom newChatRoom) {
    return new ChatRoomEntity(newChatRoom.goodsId(), newChatRoom.seller().id(), buyer.id());
  }

  public Long getSellerId() {
    return sellerId;
  }

  public Long getBuyerId() {
    return buyerId;
  }

  public Long getGoodsId() {
    return goodsId;
  }
}
