package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfileService;
import com.ssafy.s12p21d206.achu.chat.domain.validator.ChatValidator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatMessageRepository messageRepository;
  private final ChatUserProfileService chatUserProfileService;
  private final ChatValidator validator;
  private final ChatMessageReadCounter chatMessageReadCounter;
  private final ChatRoomReader chatRoomReader;

  public ChatRoomService(
      ChatRoomRepository chatRoomRepository,
      ChatMessageRepository messageRepository,
      ChatUserProfileService chatUserProfileService,
      ChatValidator validator,
      ChatMessageReadCounter chatMessageReadCounter,
      ChatRoomReader chatRoomReader) {
    this.chatRoomRepository = chatRoomRepository;
    this.messageRepository = messageRepository;
    this.chatUserProfileService = chatUserProfileService;
    this.validator = validator;
    this.chatMessageReadCounter = chatMessageReadCounter;
    this.chatRoomReader = chatRoomReader;
  }

  @Transactional
  public ChatRoom createRoom(ChatUser user, ChatRoomCreationRequest request) {
    Long userId = user.id();
    Long receiverId = request.receiverId();

    // 이미 존재하는 채팅방 확인
    Optional<Long> existingRoomId =
        chatRoomRepository.findRoomIdByUsers(user, new ChatUser(receiverId));
    if (existingRoomId.isPresent()) {
      // 기존 채팅방이 있으면 해당 채팅방 반환
      return chatRoomRepository
          .findById(existingRoomId.get())
          .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_ROOM_NOT_FOUND));
    }

    // 새 채팅방 생성
    ChatRoom chatRoom = ChatRoom.create(null, user, ChatUser.of(receiverId), LocalDateTime.now());
    ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

    // JOIN 메시지는 더 이상 생성하지 않음
    // 필요한 경우 아래 코드 사용
    // messageRepository.save(NewChatMessage.join(savedRoom.id(), user));

    return savedRoom;
  }

  @Transactional(readOnly = true)
  public List<ChatRoomSummary> getRoomList(ChatUser user) {
    Long userId = user.id();

    // 유저가 참여 중인 활성화된 채팅방 목록 조회
    List<ChatRoom> activeRooms = chatRoomRepository.findActiveByUserId(user);

    if (activeRooms.isEmpty()) {
      return List.of();
    }

    List<Long> roomIds = activeRooms.stream().map(ChatRoom::id).collect(Collectors.toList());

    // 최신 메시지 조회
    List<ChatMessage> latestMessages = messageRepository.findLatestByRoomIds(roomIds);
    Map<Long, ChatMessage> messageByRoomId =
        latestMessages.stream().collect(Collectors.toMap(ChatMessage::roomId, message -> message));

    // 채팅방별 상대방 정보 및 안 읽은 메시지 수 조회
    return activeRooms.stream()
        .map(room -> {
          Long roomId = room.id();

          // 상대방 찾기
          ChatUser partner = room.getPartnerOf(user);
          Long partnerId = partner.id();

          // 현재 사용자의 마지막 읽은 메시지 ID 찾기
          Long lastReadMessageId = chatRoomRepository
              .findLastReadMessageIdByRoomIdAndUserId(roomId, user)
              .orElse(null);

          // 상대방 프로필 정보 조회 (실패해도 기본 프로필로 대체)
          ChatUserProfile partnerProfile =
              chatUserProfileService.getUserProfile(new ChatUser(partnerId));

          // 상대방 활성화 상태 조회
          boolean partnerActive = false;
          if (room.user1().id().equals(partnerId)) {
            partnerActive = room.user1Active();
          } else if (room.user2().id().equals(partnerId)) {
            partnerActive = room.user2Active();
          }

          // 안 읽은 메시지 수 계산
          long unreadCount = messageRepository.countUnreadMessages(roomId, user, lastReadMessageId);

          // 최신 메시지 정보
          ChatMessage latestMessage = messageByRoomId.get(roomId);
          String lastMessageContent = latestMessage != null ? latestMessage.content() : "";
          LocalDateTime lastMessageTime =
              latestMessage != null ? latestMessage.timestamp() : LocalDateTime.now();

          return new ChatRoomSummary(
              roomId,
              partnerId,
              partnerProfile.nickname(),
              partnerProfile.profileImageUrl(),
              lastMessageContent,
              lastMessageTime,
              unreadCount,
              partnerActive);
        })
        .sorted((a, b) -> b.lastMessageTime().compareTo(a.lastMessageTime())) // 최신 메시지 순으로 정렬
        .collect(Collectors.toList());
  }

  @Transactional
  public ChatRoom leaveRoom(ChatUser user, Long roomId) {
    // 사용자의 채팅방 참여 및 활성 상태 검증
    validator.validateUserActive(roomId, user);

    ChatRoom updatedRoom = chatRoomRepository.leaveRoom(roomId, user);

    // 퇴장 메시지 저장
    messageRepository.save(NewChatMessage.leave(roomId, user));

    return updatedRoom;
  }

  @Transactional(readOnly = true)
  public UnreadCount getUnreadCount(ChatUser user) {
    List<ChatRoom> activeRooms = chatRoomReader.findActiveRooms(user);
    return chatMessageReadCounter.countUnread(activeRooms, user);
  }
}
