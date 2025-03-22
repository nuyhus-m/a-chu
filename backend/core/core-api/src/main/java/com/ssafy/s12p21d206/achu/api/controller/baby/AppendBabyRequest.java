package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.domain.NewBaby;
import com.ssafy.s12p21d206.achu.domain.support.Sex;
import java.time.LocalDate;

public record AppendBabyRequest(String nickname, Sex gender, LocalDate birth) {

  public NewBaby toNewBaby(String imageUrl) {
    return new NewBaby(this.nickname(), this.gender(), imageUrl, this.birth());
  }
}
