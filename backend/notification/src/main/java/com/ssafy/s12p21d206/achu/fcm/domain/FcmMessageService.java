package com.ssafy.s12p21d206.achu.fcm.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FcmMessageService {
  private final FcmTokenReader fcmTokenReader;
  private final FcmNotifier fcmNotifier;
  private static final Logger log = LoggerFactory.getLogger(FcmMessageService.class);

  public FcmMessageService(FcmTokenReader fcmTokenReader, FcmNotifier fcmNotifier) {
    this.fcmTokenReader = fcmTokenReader;
    this.fcmNotifier = fcmNotifier;
  }

  public void sendTradeCompleteMessage(Long buyerId, Long tradeId) {
    fcmTokenReader
        .readUserToken(buyerId)
        .ifPresentOrElse(
            token -> {
              FcmMessage message = new FcmMessage(
                  token.fcmToken(),
                  "🎉 거래가 완료되었어요!",
                  "판매자와의 거래가 무사히 마무리되었어요 😊",
                  Map.of(
                      "targetFragment",
                      "TradeList",
                      "requestId",
                      tradeId.toString(),
                      "type",
                      "TRADE_COMPLETED"));
              fcmNotifier.send(message, token.userId());
            },
            () -> log.warn("📭 FCM 토큰 없음(거래완료)- 알림 생략: userId={}", buyerId));
  }

  public void sendPriceChangeLikeMessage(Set<Long> userIds, Long goodsId, String goodsTitle) {
    List<FcmToken> fcmTokens = fcmTokenReader.readUserTokens(userIds);

    String title = "\uD83D\uDD14 관심 상품의 가격이 변동되었어요.";
    String body = "\uD83D\uDCB8 '" + goodsTitle + "'" + "의 가격이 변경되었어요. 지금 확인해보세요!";

    Map<String, String> data = Map.of(
        "targetFragment",
        "ProductDetail",
        "requestId",
        goodsId.toString(),
        "type",
        "PRICE_CHANGE_LIKE");

    for (FcmToken token : fcmTokens) {
      fcmNotifier.send(new FcmMessage(token.fcmToken(), title, body, data), token.userId());
    }
  }

  public void sendPriceChangeChatMessage(Set<Long> userIds, Long goodsId, String goodsTitle) {
    List<FcmToken> fcmTokens = fcmTokenReader.readUserTokens(userIds);
    String title = "\uD83D\uDD14 채팅하던 상품의 가격이 변동되었어요.";
    String body = "\uD83D\uDCB8 '" + goodsTitle + "'" + "의 가격이 변경되었어요. 지금 확인해보세요!";

    Map<String, String> data = Map.of(
        "targetFragment",
        "ProductDetail",
        "requestId",
        goodsId.toString(),
        "type",
        "PRICE_CHANGE_CHAT");

    for (FcmToken token : fcmTokens) {
      fcmNotifier.send(new FcmMessage(token.fcmToken(), title, body, data), token.userId());
    }
  }

  public void sendChatRoomCreateMessage(Long userId, Long chatRoomId, String goodsTitle) {
    fcmTokenReader
        .readUserToken(userId)
        .ifPresentOrElse(
            token -> {
              FcmMessage message = new FcmMessage(
                  token.fcmToken(),
                  "\uD83D\uDCAC 새 채팅이 도착했어요!",
                  "'" + goodsTitle + "'" + "에 관심 있는 사용자가 말을 걸었어요. 지금 바로 답장을 남겨보세요!",
                  Map.of(
                      "targetFragment",
                      "Chat",
                      "requestId",
                      chatRoomId.toString(),
                      "type",
                      "CHATROOM_CREATE"));
              fcmNotifier.send(message, token.userId());
            },
            () -> log.warn("📭 FCM 토큰 없음(채팅방생성) - 알림 생략: userId={}", userId));
  }

  public void sendTradeCompleteWisherMessage(Set<Long> userIds, Long goodsId) {
    List<FcmToken> fcmTokens = fcmTokenReader.readUserTokens(userIds);
    String title = "\uD83D\uDC94 관심 있던 상품이 거래 완료됐어요!";
    String body = "비슷한 상품도 구경해보세요!";

    Map<String, String> data = Map.of(
        "targetFragment",
        "LikeList",
        "requestId",
        goodsId.toString(),
        "type",
        "TRADE_COMPLETE_WISHER");

    for (FcmToken token : fcmTokens) {
      fcmNotifier.send(new FcmMessage(token.fcmToken(), title, body, data), token.userId());
    }
  }

  public void sendNewChatMessage(
      Long userId, String senderNickname, Long chatRoomId, String content) {
    fcmTokenReader
        .readUserToken(userId)
        .ifPresentOrElse(
            token -> {
              String title = "💌 " + senderNickname + "님이 메시지를 보냈어요";
              String body = content.length() > 15 ? content.substring(0, 15) + "..." : content;
              FcmMessage message = new FcmMessage(
                  token.fcmToken(),
                  title,
                  body,
                  Map.of(
                      "targetFragment",
                      "Chat",
                      "requestId",
                      chatRoomId.toString(),
                      "type",
                      "NEW_CHAT_MESSAGE"));
              fcmNotifier.send(message, token.userId());
            },
            () -> log.warn("📭 FCM 토큰 없음(새 채팅 메세지 알림) - 알림 생략: userId={}", userId));
  }
}
