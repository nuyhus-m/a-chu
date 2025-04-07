package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import java.util.Set;

public interface CoreChatRoomRepository {

  Set<Long> findChatUserIds(Long goodsId);

  List<ChatRoomCountStatus> status(List<Long> goodsIds);
}
