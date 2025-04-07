package com.ssafy.s12p21d206.achu.fcm.controller.event;

import com.ssafy.s12p21d206.achu.PriceChangeLikeEvent;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmMessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PriceChangeLikeEventListener {

  private final FcmMessageService fcmMessageService;

  public PriceChangeLikeEventListener(FcmMessageService fcmMessageService) {
    this.fcmMessageService = fcmMessageService;
  }

  @EventListener
  public void publishNotification(PriceChangeLikeEvent event) {
    fcmMessageService.sendPriceChangeLikeMessage(
        event.getLikerIds(), event.getGoodsId(), event.getGoodsTitle());
  }
}
