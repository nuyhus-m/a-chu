package com.ssafy.s12p21d206.achu.chat.domain.validator;

import com.ssafy.s12p21d206.achu.chat.domain.ChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.ChatMessageRepository;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomRepository;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 채팅 시스템의 검증 로직을 담당하는 컴포넌트
 * 중복되는 검증 로직을 한 곳에서 관리하여 코드 중복을 줄이고 일관성을 유지합니다.
 */
@Component
public class ChatValidator {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatMessageRepository messageRepository;

  public ChatValidator(
      ChatRoomRepository chatRoomRepository, ChatMessageRepository messageRepository) {
    this.chatRoomRepository = chatRoomRepository;
    this.messageRepository = messageRepository;
  }

  public void validateUserInRoom(Long roomId, ChatUser user) {
    if (!chatRoomRepository.isUserInRoom(roomId, user)) {
      throw new ChatException(ChatErrorType.USER_NOT_IN_CHAT_ROOM);
    }
  }

  public void validateUserActive(Long roomId, ChatUser user) {
    validateUserInRoom(roomId, user);

    if (!chatRoomRepository.isActiveForUser(roomId, user)) {
      throw new ChatException(ChatErrorType.USER_ALREADY_LEFT_CHAT_ROOM);
    }
  }

  public void validateExists(Long messageId) {
    if (!messageRepository.existsById(messageId)) {
      throw new ChatException(ChatErrorType.MESSAGE_NOT_FOUND);
    }
  }

  public void validateMessageInRoom(ChatMessage message, Long roomId) {
    if (!message.roomId().equals(roomId)) {
      throw new ChatException(ChatErrorType.MESSAGE_NOT_IN_CHAT_ROOM);
    }
  }

  public void validateMessageContent(String content) {
    if (!StringUtils.hasText(content)) {
      throw new ChatException(ChatErrorType.EMPTY_MESSAGE_CONTENT);
    }
  }

  public ChatUser validateAndGetPartnerId(Long roomId, ChatUser user) {
    return chatRoomRepository
        .findPartnerIdByRoomIdAndUserId(roomId, user)
        .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_PARTNER_NOT_FOUND));
  }
}
