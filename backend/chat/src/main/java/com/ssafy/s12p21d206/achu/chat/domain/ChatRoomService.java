package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.support.CollectionUtils;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserProfile;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUserReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

  private final ChatRoomAppender chatRoomAppender;
  private final ChatRoomReader chatRoomReader;
  private final ChatUserReader chatUserReader;

  public ChatRoomService(
      ChatRoomAppender chatRoomAppender,
      ChatRoomReader chatRoomReader,
      ChatUserReader chatUserReader) {
    this.chatRoomAppender = chatRoomAppender;
    this.chatRoomReader = chatRoomReader;
    this.chatUserReader = chatUserReader;
  }

  public ChatRoomWithParticipant append(ChatUser buyer, NewChatRoom newChatRoom) {
    ChatRoom chatRoom = chatRoomAppender.append(buyer, newChatRoom);
    ChatUserProfile buyerProfile = chatUserReader.readUserProfile(buyer);
    ChatUserProfile sellerProfile = chatUserReader.readUserProfile(chatRoom.seller());
    return new ChatRoomWithParticipant(chatRoom, buyerProfile, sellerProfile);
  }

  public List<ChatRoomWithParticipant> readChatRoomWithParticipants(ChatUser viewer) {
    List<ChatRoom> chatRooms = chatRoomReader.readChatRooms(viewer);
    Set<ChatUser> chatUsers =
        CollectionUtils.flatMapToSet(chatRooms, room -> List.of(room.seller(), room.buyer()));
    List<ChatUserProfile> chatUserProfiles = chatUserReader.readUserProfiles(chatUsers);
    return buildChatRoomsWithParticipants(chatRooms, chatUserProfiles);
  }

  private List<ChatRoomWithParticipant> buildChatRoomsWithParticipants(
      List<ChatRoom> chatRooms, List<ChatUserProfile> chatUserProfiles) {
    Map<ChatUser, ChatUserProfile> profileMap =
        CollectionUtils.toMap(chatUserProfiles, profile -> new ChatUser(profile.id()));
    return chatRooms.stream()
        .map(room -> new ChatRoomWithParticipant(
            room, profileMap.get(room.seller()), profileMap.get(room.buyer())))
        .collect(Collectors.toList());
  }
}
