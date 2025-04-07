package com.ssafy.s12p21d206.achu.fcm.controller.event;

import com.ssafy.s12p21d206.achu.TradeCompleteWisherEvent;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmMessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TradeCompleteWisherEventListener {

  private final FcmMessageService fcmMessageService;

  public TradeCompleteWisherEventListener(FcmMessageService fcmMessageService) {
    this.fcmMessageService = fcmMessageService;
  }

  @EventListener
  public void publishNotification(TradeCompleteWisherEvent event) {
    fcmMessageService.sendTradeCompleteWisherMessage(event.getLikerId(), event.getGoodsId());
  }
}
