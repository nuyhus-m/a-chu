package com.ssafy.s12p21d206.achu.storage.db.core.chat;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageType;
import com.ssafy.s12p21d206.achu.chat.domain.ParticipantStatus;
import com.ssafy.s12p21d206.achu.chat.domain.goods.Goods;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import java.time.LocalDateTime;

public class ChatRoomDto {
  private final Long id;
  private final Long goodsId;
  private final Long sellerId;
  private final Long buyerId;
  private final Long sellerLastReadMessageId;
  private final Long buyerLastReadMessageId;
  private final boolean isSellerLeft;
  private final boolean isBuyerLeft;
  private final String goodsTitle;
  private final String goodsThumbnailImageUrl;
  private final String sellerNickname;
  private final String sellerProfileImageUrl;
  private final String buyerNickname;
  private final String buyerProfileImageUrl;
  private final Long lastMessageId;
  private final String lastMessageContent;
  private final Long lastMessageSenderId;
  private final MessageType lastMessageType;
  private final LocalDateTime lastMessageCreatedAt;

  public ChatRoomDto(
      Long id,
      Long goodsId,
      Long sellerId,
      Long buyerId,
      Long sellerLastReadMessageId,
      Long buyerLastReadMessageId,
      boolean isSellerLeft,
      boolean isBuyerLeft,
      String goodsTitle,
      String goodsThumbnailImageUrl,
      String sellerNickname,
      String sellerProfileImageUrl,
      String buyerNickname,
      String buyerProfileImageUrl,
      Long lastMessageId,
      String lastMessageContent,
      Long lastMessageSenderId,
      MessageType lastMessageType,
      LocalDateTime lastMessageCreatedAt) {
    this.id = id;
    this.goodsId = goodsId;
    this.sellerId = sellerId;
    this.buyerId = buyerId;
    this.sellerLastReadMessageId = sellerLastReadMessageId;
    this.buyerLastReadMessageId = buyerLastReadMessageId;
    this.isSellerLeft = isSellerLeft;
    this.isBuyerLeft = isBuyerLeft;
    this.goodsTitle = goodsTitle;
    this.goodsThumbnailImageUrl = goodsThumbnailImageUrl;
    this.sellerNickname = sellerNickname;
    this.sellerProfileImageUrl = sellerProfileImageUrl;
    this.buyerNickname = buyerNickname;
    this.buyerProfileImageUrl = buyerProfileImageUrl;
    this.lastMessageId = lastMessageId;
    this.lastMessageContent = lastMessageContent;
    this.lastMessageSenderId = lastMessageSenderId;
    this.lastMessageType = lastMessageType;
    this.lastMessageCreatedAt = lastMessageCreatedAt;
  }

  public ChatRoom toChatRoom() {
    // 상품 정보 생성
    Goods goods = new Goods(goodsId, goodsTitle, goodsThumbnailImageUrl);

    // 판매자 프로필 생성
    ChatUserProfile sellerProfile =
        ChatUserProfile.of(sellerId, sellerNickname, sellerProfileImageUrl);

    // 구매자 프로필 생성
    ChatUserProfile buyerProfile = ChatUserProfile.of(buyerId, buyerNickname, buyerProfileImageUrl);

    // 참여자 상태 생성
    ParticipantStatus participantStatus = new ParticipantStatus(
        sellerLastReadMessageId, buyerLastReadMessageId, isSellerLeft, isBuyerLeft);

    // 마지막 메시지 생성 (lastMessageId가 null인 경우 null 반환)
    Message lastMessage = lastMessageId != null
        ? new Message(
            lastMessageId,
            id,
            lastMessageContent,
            new ChatUser(lastMessageSenderId),
            lastMessageType,
            lastMessageCreatedAt)
        : null;

    return new ChatRoom(id, goods, sellerProfile, buyerProfile, participantStatus, lastMessage);
  }
}
