package com.ssafy.s12p21d206.achu.domain;

import org.springframework.stereotype.Component;

@Component
public class PreferenceScoreAdder {
  private final PreferenceScoreRepository preferenceScoreRepository;

  public PreferenceScoreAdder(PreferenceScoreRepository preferenceScoreRepository) {
    this.preferenceScoreRepository = preferenceScoreRepository;
  }

  public void addScore(Long babyId, Long goodsId, Long score) {
    // client 에서 가격변동 알림에서 접속 시 babyId를 0으로 보내기로함
    // TODO: 추후에 가격변동 알림에서 아기 정보도 함께 보내는 것으로 변경해야함
    if (babyId == 0) {
      return;
    }
    preferenceScoreRepository.addScore(babyId, goodsId, score);
  }
}
