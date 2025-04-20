package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.api.validation.BabyNickname;
import com.ssafy.s12p21d206.achu.domain.NewBaby;
import com.ssafy.s12p21d206.achu.domain.Sex;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AppendBabyRequest(
    @NotNull @BabyNickname String nickname, @NotNull Sex gender, @NotNull LocalDate birth) {

  public NewBaby toNewBaby() {
    return new NewBaby(this.nickname(), this.gender(), this.birth());
  }
}
