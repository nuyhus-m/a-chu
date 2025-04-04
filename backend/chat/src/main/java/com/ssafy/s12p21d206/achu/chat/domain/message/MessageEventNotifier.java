package com.ssafy.s12p21d206.achu.chat.domain.message;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomReader;
import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageEventNotifier {

  private static final String NEW_MESSAGE_ARRIVED_DESTINATION_FORMAT =
      "/read/chat/users/%s/message-arrived";
  private static final String CHAT_ROOM_UPDATE_DESTINATION_FORMAT =
      "/read/chat/users/%s/rooms/update";

  private static final String NEW_MESSAGE_NOTIFICATION_PAYLOAD = "NEW_MESSAGE_ARRIVED";

  private final SimpMessagingTemplate messagingTemplate;
  private final ChatRoomReader chatRoomReader;

  public MessageEventNotifier(
      SimpMessagingTemplate messagingTemplate, ChatRoomReader chatRoomReader) {
    this.messagingTemplate = messagingTemplate;
    this.chatRoomReader = chatRoomReader;
  }

  /**
   * 메시지 도착 이벤트를 전송합니다.
   */
  public void notifyMessageArrived(ChatUser user, Message message) {
    if (message.isUserMessage()) {
      return;
    }

    messagingTemplate.convertAndSend(
        String.format(NEW_MESSAGE_ARRIVED_DESTINATION_FORMAT, user.id()),
        NEW_MESSAGE_NOTIFICATION_PAYLOAD);
  }

  public void notifyChatRoomUpdate(
      ChatUser user, ChatUser partner, ChatRoom chatRoom, Message message) {
    if (!message.isUserMessage()) {
      return;
    }

    chatRoom = chatRoom.updateLastMessage(message);
    UnreadCount partnerUnreadCount = chatRoomReader.countUnreadMessages(partner, chatRoom.id());
    UnreadCount userUnreadCount = chatRoomReader.countUnreadMessages(user, chatRoom.id());

    messagingTemplate.convertAndSend(
        String.format(CHAT_ROOM_UPDATE_DESTINATION_FORMAT, user.id()),
        ChatRoomDto.from(user, chatRoom, userUnreadCount));
    messagingTemplate.convertAndSend(
        String.format(CHAT_ROOM_UPDATE_DESTINATION_FORMAT, partner.id()),
        ChatRoomDto.from(partner, chatRoom, partnerUnreadCount));
  }
}
