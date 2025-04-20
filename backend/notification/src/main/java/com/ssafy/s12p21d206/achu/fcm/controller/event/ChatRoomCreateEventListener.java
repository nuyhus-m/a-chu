package com.ssafy.s12p21d206.achu.fcm.controller.event;

import com.ssafy.s12p21d206.achu.ChatRoomCreateEvent;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmMessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomCreateEventListener {

  private final FcmMessageService fcmMessageService;

  public ChatRoomCreateEventListener(FcmMessageService fcmMessageService) {
    this.fcmMessageService = fcmMessageService;
  }

  @EventListener
  public void publishNotification(ChatRoomCreateEvent event) {

    fcmMessageService.sendChatRoomCreateMessage(
        event.getSellerId(), event.getChatRoomId(), event.getGoodsTitle());
  }
}
