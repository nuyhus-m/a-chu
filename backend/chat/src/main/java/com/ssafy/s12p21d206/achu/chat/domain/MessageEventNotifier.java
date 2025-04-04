package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageEventNotifier {

  private static final String MESSAGE_NOTIFICATION_DESTINATION_FORMAT =
      "/read/chat/users/%s/message-arrived";
  private static final String NEW_MESSAGE_NOTIFICATION_PAYLOAD = "NEW_MESSAGE_ARRIVED";

  private final SimpMessagingTemplate messagingTemplate;

  public MessageEventNotifier(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  /**
   * 메시지 도착 이벤트를 수신자에게 전송합니다.
   */
  public void notifyMessageArrivedToPartner(ChatRoom chatRoom, Message message) {
    if (message.isUserMessage()) {
      return;
    }

    ChatUser partner = chatRoom.findPartner(message.sender());
    messagingTemplate.convertAndSend(
        String.format(MESSAGE_NOTIFICATION_DESTINATION_FORMAT, partner.id()),
        NEW_MESSAGE_NOTIFICATION_PAYLOAD);
  }
}
