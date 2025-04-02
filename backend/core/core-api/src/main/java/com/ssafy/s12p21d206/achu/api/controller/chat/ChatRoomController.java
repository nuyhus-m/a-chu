package com.ssafy.s12p21d206.achu.api.controller.chat;

import com.ssafy.s12p21d206.achu.api.controller.ApiUser;
import com.ssafy.s12p21d206.achu.domain.chat.ChatRoom;
import com.ssafy.s12p21d206.achu.domain.chat.ChatRoomService;
import com.ssafy.s12p21d206.achu.domain.chat.NewChatRoom;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatRoomController {

  private final ChatRoomService chatRoomService;

  public ChatRoomController(ChatRoomService chatRoomService) {
    this.chatRoomService = chatRoomService;
  }

  @PostMapping("/chatRoom")
  public ResponseEntity<Long> create(ApiUser apiUser, @RequestBody AppendChatRoomRequest request) {
    NewChatRoom newChatRoom = request.toNewChatRoom();
    Long id = chatRoomService.append(apiUser.toUser(), newChatRoom);
    return ResponseEntity.ok(id);
  }

  @GetMapping("/chatRoom")
  public ResponseEntity<List<ChatRoomResponse>> findMyRooms(ApiUser apiUser) {
    List<ChatRoom> chatRooms = chatRoomService.findMyRooms(apiUser.toUser());
    return ResponseEntity.ok(ChatRoomResponse.of(chatRooms, apiUser.toUser()));
  }
}
