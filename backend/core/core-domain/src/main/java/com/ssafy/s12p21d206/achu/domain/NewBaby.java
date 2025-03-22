package com.ssafy.s12p21d206.achu.domain;

import com.ssafy.s12p21d206.achu.domain.support.Sex;
import java.time.LocalDate;

public record NewBaby(String nickname, Sex gender, String imageUrl, LocalDate birth) {}
