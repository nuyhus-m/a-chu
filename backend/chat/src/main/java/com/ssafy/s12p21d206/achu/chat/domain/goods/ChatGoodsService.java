package com.ssafy.s12p21d206.achu.chat.domain.goods;

import com.ssafy.s12p21d206.achu.chat.domain.ChatRoom;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ChatGoodsService {

  private final ChatGoodsReader chatGoodsReader;

  public ChatGoodsService(ChatGoodsReader chatGoodsReader) {
    this.chatGoodsReader = chatGoodsReader;
  }

  public Goods readGoods(Long goodsId) {
    return chatGoodsReader.readById(goodsId);
  }

  public List<Goods> readGoodsIn(Set<ChatRoom> chatRooms) {
    return chatGoodsReader.readInGoodsIds(
        chatRooms.stream().map(ChatRoom::goodsId).collect(Collectors.toSet()));
  }
}
