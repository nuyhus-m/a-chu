package com.ssafy.s12p21d206.achu.domain;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
  public List<ChatStatus> findChatStatus(User user, List<Long> goodsId) {
    /*
     *
     * 각 goodsId에 대해 아래 정보를 조회해야 합니다:
     *
     * 1. 해당 상품에 연결된 전체 채팅방 개수
     *    - chatRoomRepository.countByGoodsId(goodsId)
     *
     * 결과는 goodsId를 기준으로 ChatStatus 객체로 만들어 반환해야 합니다:
     *   new ChatStatus(goodsId, chatCount)
     */
    throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
  }
}
