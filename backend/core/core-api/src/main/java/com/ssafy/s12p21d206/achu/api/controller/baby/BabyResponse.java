package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.domain.Baby;
import com.ssafy.s12p21d206.achu.domain.Sex;
import java.time.LocalDate;
import java.util.List;

public record BabyResponse(Long id, String nickname, Sex gender, String imgUrl, LocalDate birth) {

  public static BabyResponse from(Baby baby) {
    return new BabyResponse(
        baby.getId(), baby.getNickname(), baby.getGender(), baby.getImageUrl(), baby.getBirth());
  }

  public static List<BabyResponse> of(List<Baby> babies) {
    return babies.stream().map(BabyResponse::from).toList();
  }
}
