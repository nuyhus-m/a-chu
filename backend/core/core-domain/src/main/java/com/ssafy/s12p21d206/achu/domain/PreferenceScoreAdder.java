package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class PreferenceScoreAdder {
  private final PreferenceScoreRepository preferenceScoreRepository;

  public PreferenceScoreAdder(PreferenceScoreRepository preferenceScoreRepository) {
    this.preferenceScoreRepository = preferenceScoreRepository;
  }

  public void addScore(Long babyId, Long goodsId, Long score) {
    preferenceScoreRepository.addScore(babyId, goodsId, score);
  }
}
