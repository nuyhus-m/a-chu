package com.ssafy.s12p21d206.achu.chat.domain;

public record ParticipantStatus(
    Long sellerLastReadMessageId,
    Long buyerLastReadMessageId,
    boolean isSellerLeft,
    boolean isBuyerLeft) {

  public ParticipantStatus updateBuyerStatus(Message message) {
    return new ParticipantStatus(
        this.sellerLastReadMessageId, message.id(), this.isSellerLeft, this.isBuyerLeft);
  }

  public ParticipantStatus updateSellerStatus(Message message) {
    return new ParticipantStatus(
        message.id(), this.buyerLastReadMessageId, this.isSellerLeft, this.isBuyerLeft);
  }
}
