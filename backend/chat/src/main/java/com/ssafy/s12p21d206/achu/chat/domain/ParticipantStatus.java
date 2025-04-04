package com.ssafy.s12p21d206.achu.chat.domain;

public record ParticipantStatus(
    Long sellerLastReadMessageId,
    Long buyerLastReadMessageId,
    boolean isSellerLeft,
    boolean isBuyerLeft) {}
