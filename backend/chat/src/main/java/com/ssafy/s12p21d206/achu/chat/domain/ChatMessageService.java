package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.validator.ChatValidator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatMessageService {

  private final ChatMessageReader chatMessageReader;
  private final ChatMessageRepository messageRepository;
  private final ChatValidator chatValidator;
  private final ChatRoomUpdater chatRoomUpdater;

  public ChatMessageService(
      ChatMessageReader chatMessageReader,
      ChatMessageRepository messageRepository,
      ChatValidator chatValidator,
      ChatRoomUpdater chatRoomUpdater) {
    this.chatMessageReader = chatMessageReader;
    this.messageRepository = messageRepository;
    this.chatValidator = chatValidator;
    this.chatRoomUpdater = chatRoomUpdater;
  }

  @Transactional
  public ChatMessage sendMessage(ChatUser sender, Long roomId, String content) {
    // 메시지 내용 검증
    chatValidator.validateMessageContent(content);

    // 발신자의 채팅방 참여 및 활성 상태 검증
    chatValidator.validateUserActive(roomId, sender);

    // 메시지 저장
    NewChatMessage newMessage = NewChatMessage.chat(roomId, sender, content);
    ChatMessage savedMessage = messageRepository.save(newMessage);

    // 발신자의 읽음 처리
    chatRoomUpdater.updateReadStatus(sender, roomId, savedMessage.id());

    // 상대방 확인 (검증 후 ID 반환)
    chatValidator.validateAndGetPartnerId(roomId, sender);

    return savedMessage;
  }

  @Transactional
  public ChatMessage markAsRead(ChatUser user, Long roomId, Long messageId) {
    chatValidator.validateUserInRoom(roomId, user);

    ChatMessage message = chatMessageReader.readById(messageId);
    chatValidator.validateMessageInRoom(message, roomId);

    chatRoomUpdater.updateReadStatus(user, roomId, messageId);

    return message;
  }

  @Transactional
  public List<ChatMessage> readMessages(ChatUser user, Long roomId, Integer limit, Long before) {
    // 사용자의 채팅방 참여 여부 검증
    chatValidator.validateUserInRoom(roomId, user);

    // 메시지 조회
    List<ChatMessage> messages = messageRepository.findByRoomIdBeforeId(roomId, before, limit);

    // 가장 최신 메시지의 ID가 있으면 읽음 처리
    if (!messages.isEmpty()) {
      Long latestMessageId = messages.get(0).id(); // 첫 번째 메시지가 가장 최신
      chatRoomUpdater.updateReadStatus(user, roomId, latestMessageId);
    }

    return messages;
  }
}
