package com.ssafy.s12p21d206.achu.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  Logger logger = LoggerFactory.getLogger(ChatController.class);

  @MessageMapping("/chat/rooms/{chatRoomId}")
  @SendTo("/sub/chat/rooms/{chatRoomId}")
  public ChatMessage send(ChatMessage message, @DestinationVariable String chatRoomId) {
    logger.info("chatRoomId: {}", chatRoomId);
    logger.info("message: {}", message);
    return message;
  }
}
