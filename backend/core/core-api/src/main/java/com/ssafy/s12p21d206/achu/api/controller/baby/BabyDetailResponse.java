package com.ssafy.s12p21d206.achu.api.controller.baby;

import com.ssafy.s12p21d206.achu.domain.Sex;
import java.time.LocalDate;

public record BabyDetailResponse(
    Long id, String nickname, Sex gender, String imgUrl, LocalDate birth) {}
