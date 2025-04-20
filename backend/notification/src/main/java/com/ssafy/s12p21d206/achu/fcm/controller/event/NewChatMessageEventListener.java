package com.ssafy.s12p21d206.achu.fcm.controller.event;

import com.ssafy.s12p21d206.achu.NewChatMessageEvent;
import com.ssafy.s12p21d206.achu.fcm.domain.FcmMessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NewChatMessageEventListener {

  private final FcmMessageService fcmMessageService;

  public NewChatMessageEventListener(FcmMessageService fcmMessageService) {
    this.fcmMessageService = fcmMessageService;
  }

  @EventListener
  public void publishNotification(NewChatMessageEvent event) {

    fcmMessageService.sendNewChatMessage(
        event.getUserId(), event.getSenderNickname(), event.getChatRoomId(), event.getContent());
  }
}
