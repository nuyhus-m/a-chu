package com.ssafy.s12p21d206.achu.fcm.controller.event;

import com.ssafy.s12p21d206.achu.TradeCompleteEvent;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmMessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TradeCompleteEventListener {

  private final FcmMessageService fcmMessageService;

  public TradeCompleteEventListener(FcmMessageService fcmMessageService) {
    this.fcmMessageService = fcmMessageService;
  }

  @EventListener
  public void publishNotification(TradeCompleteEvent event) {
    fcmMessageService.sendTradeCompleteMessage(event.getBuyerId(), event.getTradeId());
  }
}
