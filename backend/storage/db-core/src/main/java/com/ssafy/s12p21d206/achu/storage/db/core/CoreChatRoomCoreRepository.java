package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.CoreChatRoomRepository;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class CoreChatRoomCoreRepository implements CoreChatRoomRepository {
  private final CoreChatRoomJpaRepository coreChatRoomJpaRepository;

  public CoreChatRoomCoreRepository(CoreChatRoomJpaRepository coreChatRoomJpaRepository) {
    this.coreChatRoomJpaRepository = coreChatRoomJpaRepository;
  }

  @Override
  public Set<Long> findChatUserIds(Long goodsId) {
    return coreChatRoomJpaRepository.findByGoodsIdAndEntityStatus(goodsId, EntityStatus.ACTIVE);
  }
}
