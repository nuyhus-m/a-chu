package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.domain.support.Sex;
import java.time.LocalDate;

public record ModifyBabyRequest(String nickname, Sex gender, LocalDate birth) {}
