package com.ssafy.s12p21d206.achu.storage.db.core;

import com.ssafy.s12p21d206.achu.domain.LikeRepository;
import com.ssafy.s12p21d206.achu.domain.LikeStatus;
import com.ssafy.s12p21d206.achu.domain.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class LikeCoreRepository implements LikeRepository {

  private final LikeJpaRepository likeJpaRepository;

  public LikeCoreRepository(LikeJpaRepository likeJpaRepository) {
    this.likeJpaRepository = likeJpaRepository;
  }

  @Override
  public int count(Long goodsId) {
    return likeJpaRepository.countByGoodsIdAndEntityStatus(goodsId, EntityStatus.ACTIVE);
  }

  @Override
  public boolean isLike(User user, Long goodsId) {
    return likeJpaRepository.existsByUserIdAndGoodsIdAndEntityStatus(
        user.id(), goodsId, EntityStatus.ACTIVE);
  }

  @Override
  public Map<Long, LikeStatus> status(User user, List<Long> goodsIds) {
    Map<Long, Integer> countMap = likeJpaRepository.countIn(goodsIds, EntityStatus.ACTIVE).stream()
        .collect(Collectors.toMap(e -> (Long) e[0], e -> (Integer) e[1]));
    Set<Long> likedGoods = goodsIds.isEmpty()
        ? Set.of()
        : likeJpaRepository.findLikedGoodsByUser(user.id(), goodsIds, EntityStatus.ACTIVE);

    return goodsIds.stream()
        .collect(Collectors.toMap(
            id -> id, id -> new LikeStatus(countMap.getOrDefault(id, 0), likedGoods.contains(id))));
  }

  @Transactional
  @Override
  public void like(User user, Long goodsId) {
    LikeEntity likeEntity = likeJpaRepository
        .findByUserIdAndGoodsId(user.id(), goodsId)
        .orElseGet(() -> likeJpaRepository.save(new LikeEntity(user.id(), goodsId)));
    likeEntity.active();
  }

  @Transactional
  @Override
  public void deleteLike(User user, Long goodsId) {
    Optional<LikeEntity> likeEntityOptional =
        likeJpaRepository.findByUserIdAndGoodsId(user.id(), goodsId);

    // likeEntityOptional.ifPresent(BaseEntity::delete);
    if (likeEntityOptional.isEmpty()) {
      return;
    }

    LikeEntity likeEntity = likeEntityOptional.get();
    likeEntity.delete();
  }
}
