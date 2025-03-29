package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.SortType;
import java.util.List;

public interface GoodsRepository {
  List<Goods> findGoods(User user, Long offset, Long limit, SortType sort);

  List<Goods> findCategoryGoods(User user, Long categoryId, Long offset, Long limit, SortType sort);

  List<Goods> searchGoods(User user, String keyword, Long offset, Long limit, SortType sort);

  List<Goods> searchCategoryGoods(
      User user, Long categoryId, String keyword, Long offset, Long limit, SortType sort);

  List<GoodsDetail> findGoodsDetails(List<Long> goodsIds);

  GoodsDetail findGoodsDetail(Long goodsId);

  User findUserIdByGoodsId(Long goodsId);

  GoodsDetail save(User user, NewGoods newGoods);

  GoodsDetail modifyGoods(Long goodsId, ModifyGoods modifyGoods);

  // TODO: 메서드명 EntityStatus 들어가 있는데 이거 뗴야하는게 맞을듯? -> 도메인에서는 당연히 메서드 시그니처를 보고 안지워진것만 가저올거라고 예상하기 떄문에
  // 그리고 파라미터로 받지도 않고 있음
  boolean existsById(Long goodsId);

  boolean existsByIdAndUserId(Long goodsId, Long userId);

  boolean isSelling(Long goodsId);

  Long delete(Long goodsId);
}
