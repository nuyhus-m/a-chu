package com.ssafy.s12p21d206.achu.chat.domain;

import com.ssafy.s12p21d206.achu.chat.domain.goods.ChatGoodsService;
import com.ssafy.s12p21d206.achu.chat.domain.goods.Goods;
import com.ssafy.s12p21d206.achu.chat.domain.support.CollectionUtils;
import com.ssafy.s12p21d206.achu.chat.domain.user.ChatUser;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomFacade {

  private final ChatRoomService chatRoomService;
  private final ChatGoodsService chatGoodsService;
  private final MessageService messageService;

  public ChatRoomFacade(
      ChatRoomService chatRoomService,
      ChatGoodsService chatGoodsService,
      MessageService messageService) {
    this.chatRoomService = chatRoomService;
    this.chatGoodsService = chatGoodsService;
    this.messageService = messageService;
  }

  @Transactional
  public ChatRoomDetail append(ChatUser buyer, NewChatRoom newChatRoom, NewMessage newMessage) {
    Goods goods = chatGoodsService.readGoods(newChatRoom.goodsId());
    ChatRoomWithParticipant chatRoomWithParticipant = chatRoomService.append(buyer, newChatRoom);
    Message message = messageService.append(buyer, chatRoomWithParticipant.chatRoom(), newMessage);
    return new ChatRoomDetail(chatRoomWithParticipant, goods, message);
  }

  public List<ChatRoomDetail> findChatRoomDetails(ChatUser viewer) {
    List<ChatRoomWithParticipant> chatRoomWithParticipants =
        chatRoomService.readChatRoomWithParticipants(viewer);

    List<ChatRoom> chatRooms =
        CollectionUtils.mapToList(chatRoomWithParticipants, ChatRoomWithParticipant::chatRoom);

    List<Message> lastMessages = messageService.readLastMessagesIn(chatRooms);
    Map<Long, Message> lastMessageMap = CollectionUtils.toMap(lastMessages, Message::chatRoomId);

    Set<ChatRoom> chatRoomSet = CollectionUtils.toSet(chatRooms);
    List<Goods> goods = chatGoodsService.readGoodsIn(chatRoomSet);
    Map<Long, Goods> goodsMap = CollectionUtils.toMap(goods, Goods::id);

    return chatRoomWithParticipants.stream()
        .map(room -> new ChatRoomDetail(
            room,
            goodsMap.get(room.chatRoom().goodsId()),
            lastMessageMap.get(room.chatRoom().id())))
        .toList();
  }
}
