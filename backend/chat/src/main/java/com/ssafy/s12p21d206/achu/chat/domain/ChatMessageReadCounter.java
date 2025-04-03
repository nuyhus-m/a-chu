package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageReadCounter {

  private final ChatMessageRepository messageRepository;

  public ChatMessageReadCounter(ChatMessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public UnreadCount countUnread(List<ChatRoom> activeRooms, ChatUser user) {
    Map<Long, Long> roomUnread = new HashMap<>();
    long totalUnread = 0;

    for (ChatRoom room : activeRooms) {
      Long roomId = room.id();
      Long lastReadMessageId = null;

      if (room.user1().id().equals(user.id())) {
        lastReadMessageId = room.user1LastReadMessageId();
      } else if (room.user2().id().equals(user.id())) {
        lastReadMessageId = room.user2LastReadMessageId();
      }

      long unreadCount = messageRepository.countUnreadMessages(roomId, user, lastReadMessageId);
      if (unreadCount > 0) {
        roomUnread.put(roomId, unreadCount);
        totalUnread += unreadCount;
      }
    }

    return UnreadCount.of(totalUnread, roomUnread);
  }
}
