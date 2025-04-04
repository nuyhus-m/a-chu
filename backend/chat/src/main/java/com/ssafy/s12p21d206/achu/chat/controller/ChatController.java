package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  private final MessageService messageService;

  public ChatController(MessageService messageService) {
    this.messageService = messageService;
  }

  @SendTo("/read/chat/rooms/{roomId}/messages")
  @MessageMapping("/chat/rooms/{roomId}/messages")
  public MessageDto handleMessage(
      ChatApiUser chatApiUser, @DestinationVariable Long roomId, AppendMessageRequest request) {
    Message message =
        messageService.append(chatApiUser.toChatUser(), roomId, request.toNewMessage());
    return MessageDto.fromMessage(message);
  }

  @SendTo("/read/chat/rooms/{roomId}/messages/read")
  @MessageMapping("/chat/rooms/{roomId}/messages/read")
  public LastReadMessageDto handleReadMessage(
      ChatApiUser chatApiUser, @DestinationVariable Long roomId, ReadMessageRequest request) {
    messageService.updateRead(chatApiUser.toChatUser(), roomId, request.lastReadMessageId());
    return new LastReadMessageDto(chatApiUser.id(), request.lastReadMessageId());
  }
}
