package com.ssafy.s12p21d206.achu.domain;

public interface PreferenceScoreRepository {

  void addScore(Long babyId, Long goodsId, Long score);
}
