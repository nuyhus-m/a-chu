package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.error.ChatErrorType;
import com.ssafy.s12p21d206.achu.chat.domain.error.ChatException;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomReader {

  private final ChatRoomRepository chatRoomRepository;

  public ChatRoomReader(ChatRoomRepository chatRoomRepository) {
    this.chatRoomRepository = chatRoomRepository;
  }

  public List<ChatRoom> readChatRooms(ChatUser viewer) {
    return chatRoomRepository.readChatRooms(viewer);
  }

  public ChatRoom read(Long chatRoomId) {
    return chatRoomRepository
        .findById(chatRoomId)
        .orElseThrow(() -> new ChatException(ChatErrorType.CHAT_ROOM_NOT_FOUND));
  }

  public UnreadCount countUnreadMessages(ChatUser user) {
    return chatRoomRepository.countUnreadMessages(user);
  }

  public Map<Long, UnreadCount> countUnreadMessages(ChatUser user, List<Long> chatRoomIds) {
    return chatRoomRepository.countUnreadMessages(user, chatRoomIds);
  }

  public UnreadCount countUnreadMessages(ChatUser user, Long chatRoomId) {
    return chatRoomRepository.countUnreadMessages(user, chatRoomId);
  }

  public Long findChatRoomId(Long goodsId, ChatUser seller, ChatUser buyer) {
    return chatRoomRepository.findChatRoomId(goodsId, seller, buyer);
  }
}
