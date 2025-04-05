package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.controller.response.ChatApiResponse;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomFacade;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomService;
import com.ssafy.s12p21d206.achu.chat.domain.Message;
import com.ssafy.s12p21d206.achu.chat.domain.MessageService;
import com.ssafy.s12p21d206.achu.chat.domain.UnreadCount;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatRoomController {

  private final ChatRoomFacade chatRoomFacade;
  private final ChatRoomService chatRoomService;
  private final MessageService messageService;

  public ChatRoomController(
      ChatRoomFacade chatRoomFacade,
      ChatRoomService chatRoomService,
      MessageService messageService) {
    this.chatRoomFacade = chatRoomFacade;
    this.chatRoomService = chatRoomService;
    this.messageService = messageService;
  }

  @PostMapping("/chat/rooms")
  public ChatApiResponse<DefaultIdResponse> appendChatRoom(
      ChatApiUser apiUser, @RequestBody AppendChatRoomRequest request) {
    Long chatRoomId = chatRoomFacade.append(
        apiUser.toChatUser(), request.toNewChatRoom(), request.toNewMessage());
    return ChatApiResponse.success(DefaultIdResponse.of(chatRoomId));
  }

  @GetMapping("/chat/rooms")
  public ChatApiResponse<List<ChatRoomResponse>> findChatRooms(ChatApiUser apiUser) {
    List<ChatRoom> chatRooms = chatRoomService.findChatRooms(apiUser.toChatUser());
    Map<Long, UnreadCount> unreadCountMap = chatRoomService.findUnreadCounts(
        apiUser.toChatUser(), chatRooms.stream().map(ChatRoom::id).toList());
    return ChatApiResponse.success(
        ChatRoomResponse.from(apiUser.toChatUser(), chatRooms, unreadCountMap));
  }

  @GetMapping("/chat/unread-count")
  public ChatApiResponse<UnreadCountResponse> unreadCount(ChatApiUser apiUser) {
    UnreadCount unreadCount = chatRoomService.countUnreadMessages(apiUser.toChatUser());
    UnreadCountResponse response = UnreadCountResponse.of(unreadCount);
    return ChatApiResponse.success(response);
  }

  @GetMapping("/chat/rooms/{roomId}/messages")
  public ChatApiResponse<List<MessageResponse>> getMessages(
      ChatApiUser apiUser, @PathVariable Long roomId) {
    List<Message> messages = messageService.getMessagesByChatRoomId(apiUser.toChatUser(), roomId);
    return ChatApiResponse.success(MessageResponse.listFrom(messages, apiUser.toChatUser()));
  }

  @GetMapping("/chat/rooms/existence")
  public ChatApiResponse<DefaultIdResponse> checkChatRoomExistence(
      ChatApiUser apiUser, @RequestParam Long goodsId, @RequestParam Long sellerId) {

    Long chatRoomId =
        chatRoomService.findChatRoomId(goodsId, new ChatUser(sellerId), apiUser.toChatUser());
    return ChatApiResponse.success(chatRoomId != null ? DefaultIdResponse.of(chatRoomId) : null);
  }
}
