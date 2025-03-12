package com.ssafy.s12p21d206.achu.api.controller.baby;

import java.time.LocalDate;

public record AppendBabyRequest(String nickname, String gender, String imgUrl, LocalDate birth) {}
