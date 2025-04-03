package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.controller.response.MessageDto;
import com.ssafy.s12p21d206.achu.chat.controller.support.WebSocketMessageHandler;
import com.ssafy.s12p21d206.achu.chat.domain.ChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.ChatMessageService;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomService;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfileService;
import com.ssafy.s12p21d206.achu.chat.domain.validator.ChatValidator;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatStompController {

  private static final String DESTINATION_CHAT_ROOM_TOPIC = "/topic/chat/room/%d";
  private static final String DESTINATION_CHAT_ROOMS_STATUS = "/users/%d/chat-rooms/status";

  private final SimpMessagingTemplate messagingTemplate;
  private final ChatMessageService messageService;
  private final ChatRoomService chatRoomService;
  private final ChatUserProfileService userProfileService;
  private final ChatValidator validator;

  public ChatStompController(
      SimpMessagingTemplate messagingTemplate,
      ChatMessageService messageService,
      ChatRoomService chatRoomService,
      ChatUserProfileService userProfileService,
      ChatValidator validator) {
    this.messagingTemplate = messagingTemplate;
    this.messageService = messageService;
    this.chatRoomService = chatRoomService;
    this.userProfileService = userProfileService;
    this.validator = validator;
  }

  @WebSocketMessageHandler
  @SendTo("/chat/rooms/{roomId}")
  @MessageMapping("/chat/rooms/{roomId}")
  public MessageDto handleMessage(
      ChatApiUser apiUser, @DestinationVariable Long roomId, @Payload MessageSendRequest request) {
    ChatMessage message =
        messageService.sendMessage(apiUser.toChatUser(), roomId, request.content());
    ChatUserProfile senderProfile = userProfileService.getUserProfile(apiUser.toChatUser());
    // TODO: 파트너에게 메시지 알림 전송
    return MessageDto.fromMessage(message, senderProfile);
  }

  @WebSocketMessageHandler
  @MessageMapping("/chat/rooms/{roomId}/messages/{lastMessageId}/read")
  public void markAsRead(
      ChatApiUser apiUser, @DestinationVariable Long roomId, @DestinationVariable Long messageId) {
    ChatMessage message = messageService.markAsRead(apiUser.toChatUser(), roomId, messageId);
    sendReadReceipt(apiUser.toChatUser(), message.id());

    ChatUser partner = validator.validateAndGetPartnerId(roomId, apiUser.toChatUser());
    sendReadReceipt(partner, message.id());
  }

  public void sendReadReceipt(ChatUser user, Long messageId) {
    String destination = String.format(DESTINATION_CHAT_ROOMS_STATUS, user.id());
    messagingTemplate.convertAndSend(destination, messageId);
  }
}
