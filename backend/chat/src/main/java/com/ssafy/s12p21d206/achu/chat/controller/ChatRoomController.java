package com.ssafy.s12p21d206.achu.chat.controller;

import com.ssafy.s12p21d206.achu.chat.controller.response.ChatApiResponse;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomDetail;
import com.ssafy.s12p21d206.achu.chat.domain.ChatRoomFacade;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRoomController {

  private final ChatRoomFacade chatRoomFacade;

  public ChatRoomController(ChatRoomFacade chatRoomFacade) {
    this.chatRoomFacade = chatRoomFacade;
  }

  @PostMapping("/chat/rooms")
  public ChatApiResponse<ChatRoomResponse> appendChatRoom(
      ChatApiUser apiUser, @RequestBody AppendChatRoomRequest request) {
    ChatRoomDetail chatRoomDetail = chatRoomFacade.append(
        apiUser.toChatUser(), request.toNewChatRoom(), request.toNewMessage());
    return ChatApiResponse.success(ChatRoomResponse.from(apiUser.toChatUser(), chatRoomDetail));
  }

  @GetMapping("/chat/rooms")
  public ChatApiResponse<List<ChatRoomResponse>> findChatRooms(ChatApiUser apiUser) {
    List<ChatRoomDetail> chatRoomDetails = chatRoomFacade.findChatRoomDetails(apiUser.toChatUser());
    return ChatApiResponse.success(ChatRoomResponse.from(apiUser.toChatUser(), chatRoomDetails));
  }
}
