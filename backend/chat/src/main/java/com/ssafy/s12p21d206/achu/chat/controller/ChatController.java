package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.controller.response.ChatApiResponse;
import com.ssafy.s12p21d206.achu.chat.domain.ChatMessage;
import com.ssafy.s12p21d206.achu.chat.domain.ChatMessageService;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomService;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomSummary;
import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

  private final ChatRoomService chatRoomService;
  private final ChatMessageService chatMessageService;

  public ChatController(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
    this.chatRoomService = chatRoomService;
    this.chatMessageService = chatMessageService;
  }

  @GetMapping("/chat/rooms")
  public ChatApiResponse<List<ChatRoomResponse>> getRooms(ChatApiUser user) {
    List<ChatRoomSummary> roomSummaries = chatRoomService.getRoomList(user.toChatUser());
    List<ChatRoomResponse> responses = ChatRoomResponse.from(roomSummaries);
    return ChatApiResponse.success(responses);
  }

  @PostMapping("/chat/rooms")
  public ChatApiResponse<Long> createRoom(ChatApiUser user, @RequestBody ChatRoomRequest request) {
    ChatRoom chatRoom = chatRoomService.createRoom(user.toChatUser(), request.toDomain());
    return ChatApiResponse.success(chatRoom.id());
  }

  @PutMapping("/chat/rooms/{roomId}/leave")
  public ChatApiResponse<Void> leaveRoom(ChatApiUser user, @PathVariable Long roomId) {
    chatRoomService.leaveRoom(user.toChatUser(), roomId);
    return ChatApiResponse.success();
  }

  @GetMapping("/rooms/{roomId}/messages")
  public ChatApiResponse<List<ChatMessageResponse>> getMessages(
      ChatApiUser user,
      @PathVariable Long roomId,
      @RequestParam(required = false) Long before,
      @RequestParam(defaultValue = "50") int size) {
    List<ChatMessage> messages =
        chatMessageService.readMessages(user.toChatUser(), roomId, size, before);

    // 메시지 읽음 상태 계산 (실제 구현에서는 더 효율적인 방법 필요)
    List<ChatMessageResponse> responses = messages.stream()
        .map(message -> {
          boolean isRead = true; // 기본적으로 읽은 상태로 가정
          // 실제 구현에서는 메시지별 읽음 상태 계산 로직 필요
          return ChatMessageResponse.from(message, isRead);
        })
        .collect(Collectors.toList());

    return ChatApiResponse.success(responses);
  }

  @PutMapping("/rooms/{roomId}/read")
  public ChatApiResponse<Void> markAsRead(
      ChatApiUser user, @PathVariable Long roomId, @RequestBody MessageReadRequest request) {

    if (request.lastMessageId() != null) {
      // 특정 메시지 ID를 읽음 처리
      chatMessageService.markAsRead(user.toChatUser(), roomId, request.lastMessageId());
    } else {
      // 지정된 메시지 ID가 없는 경우 최신 메시지를 읽음 처리
      List<ChatMessage> messages =
          chatMessageService.readMessages(user.toChatUser(), roomId, 1, null);
      if (!messages.isEmpty()) {
        Long latestMessageId = messages.get(0).id(); // 최신 메시지 ID
        chatMessageService.markAsRead(user.toChatUser(), roomId, latestMessageId);
      }
    }

    return ChatApiResponse.success();
  }

  @GetMapping("/unread-count")
  public ResponseEntity<ChatApiResponse<UnreadCountResponse>> getUnreadCount(ChatApiUser user) {
    UnreadCount unreadCount = chatRoomService.getUnreadCount(user.toChatUser());
    return ResponseEntity.ok(ChatApiResponse.success(UnreadCountResponse.from(unreadCount)));
  }
}
