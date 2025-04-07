package com.ssafy.s12p21d206.achu.domain;

import java.util.Set;

public interface CoreChatRoomRepository {

  Set<Long> findChatUserIds(Long goodsId);
}
