package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChatRoomRepository {

  boolean existsBy(Long goodsId, ChatUser seller, ChatUser buyer);

  Long save(ChatUser buyer, NewChatRoom newChatRoom);

  List<ChatRoom> readChatRooms(ChatUser viewer);

  Optional<ChatRoom> findById(Long chatRoomId);

  boolean isParticipant(Long chatRoomId, ChatUser user);

  void updateRead(ChatUser user, Long roomId, Long lastReadMessageId);

  UnreadCount countUnreadMessages(ChatUser user);

  UnreadCount countUnreadMessages(ChatUser user, Long chatRoomId);

  Map<Long, UnreadCount> countUnreadMessages(ChatUser user, List<Long> chatRoomIds);

  Long findChatRoomId(Long goodsId, ChatUser seller, ChatUser buyer);
}
