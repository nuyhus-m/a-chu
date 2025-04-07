package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomCountService {
  private final ChatRoomCountReader chatRoomCountReader;

  public ChatRoomCountService(ChatRoomCountReader chatRoomCountReader) {
    this.chatRoomCountReader = chatRoomCountReader;
  }

  public List<ChatRoomCountStatus> status(List<Long> goodsIds) {
    return chatRoomCountReader.status(goodsIds);
  }
}
