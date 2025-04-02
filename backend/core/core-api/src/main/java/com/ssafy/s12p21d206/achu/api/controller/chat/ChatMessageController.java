package com.ssafy.s12p21d206.achu.api.controller.chat;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.domain.chat.ChatMessage;
import com.ssafy.s12p21d206.achu.domain.chat.ChatMessageService;
import com.ssafy.s12p21d206.achu.domain.chat.ChatMessageWithSender;
import com.ssafy.s12p21d206.achu.domain.chat.NewChatMessage;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessageController {

  private final ChatMessageService chatMessageService;

  public ChatMessageController(ChatMessageService chatMessageService) {
    this.chatMessageService = chatMessageService;
  }

  Logger logger = LoggerFactory.getLogger(ChatMessageController.class);

  @MessageMapping("/chat/rooms/{chatRoomId}")
  @SendTo("/sub/chat/rooms/{chatRoomId}")
  public ChatMessageDto send(
      ApiUser apiUser, SendChatMessageRequest request, @DestinationVariable Long chatRoomId) {
    logger.info("chatRoomId: {}", chatRoomId);
    logger.info("message: {}", request);
    NewChatMessage newChatMessage = new NewChatMessage(chatRoomId, apiUser.id(), request.message());
    ChatMessageWithSender chatMessageWithSender =
        chatMessageService.append(apiUser.toUser(), chatRoomId, newChatMessage);
    return ChatMessageDto.from(chatMessageWithSender);
  }

  @GetMapping("/chat-rooms/{chatRoomId}/chats")
  public List<ChatMessageDto> get(ApiUser apiUser, @PathVariable Long chatRoomId) {
    List<ChatMessage> messages = chatMessageService.findMessages(apiUser.toUser(), chatRoomId);
    return messages.stream().map(ChatMessageDto::from).toList();
  }
}
