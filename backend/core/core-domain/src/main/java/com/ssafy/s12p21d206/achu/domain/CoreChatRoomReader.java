package com.ssafy.s12p21d206.achu.domain;

import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class CoreChatRoomReader {
  private final CoreChatRoomRepository coreChatRoomRepository;

  public CoreChatRoomReader(CoreChatRoomRepository coreChatRoomRepository) {
    this.coreChatRoomRepository = coreChatRoomRepository;
  }

  public Set<Long> readChatUserIds(Long goodsId) {
    return coreChatRoomRepository.findChatUserIds(goodsId);
  }
}
