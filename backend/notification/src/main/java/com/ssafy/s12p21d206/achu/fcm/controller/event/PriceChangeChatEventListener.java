package com.ssafy.s12p21d206.achu.fcm.controller.event;

import com.ssafy.s12p21d206.achu.PriceChangeChatEvent;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmMessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeChatEventListener {
  private final FcmMessageService fcmMessageService;

  public PriceChangeChatEventListener(FcmMessageService fcmMessageService) {
    this.fcmMessageService = fcmMessageService;
  }

  @EventListener
  public void publishNotification(PriceChangeChatEvent event) {

    fcmMessageService.sendPriceChangeChatMessage(
        event.getChatUserIds(), event.getGoodsId(), event.getGoodsTitle());
  }
}
