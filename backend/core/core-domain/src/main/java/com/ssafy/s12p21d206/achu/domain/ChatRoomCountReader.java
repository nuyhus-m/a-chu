package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomCountReader {

  private final CoreChatRoomRepository coreChatRoomRepository;

  public ChatRoomCountReader(CoreChatRoomRepository coreChatRoomRepository) {
    this.coreChatRoomRepository = coreChatRoomRepository;
  }

  public List<ChatRoomCountStatus> status(List<Long> goodsIds) {
    return coreChatRoomRepository.status(goodsIds);
  }
}
